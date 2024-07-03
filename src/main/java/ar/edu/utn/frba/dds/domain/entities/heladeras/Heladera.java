package ar.edu.utn.frba.dds.domain.entities.heladeras;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.PublicadorSolicitudApertura;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.SolicitudApertura;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.GestorSuscripciones;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Heladera implements Contribucion {
  @Getter @Setter
  private Long id;
  @Setter
  private String nombre;
  @Setter
  private Direccion direccion;
  @Setter
  private LocalDateTime fechaRegistro;
  @Setter
  private int capacidadMaximaViandas;
  @Setter
  private Modelo modelo;
  private Set<Vianda> viandas;
  @Setter
  private EstadoHeladera estado;
  @Setter
  private float temperaturaEsperada;
  private List<CambioEstado> historialEstados;
  private List<CambioTemperatura> historialTemperaturas;
  private List<SolicitudApertura> solicitudesDeApertura;
  private List<Incidente> incidentes;

  public Heladera() {
    this.viandas = new HashSet<>();
    this.historialEstados = new ArrayList<>();
    this.estado = EstadoHeladera.ACTIVA;
    this.historialTemperaturas = new ArrayList<>();
    this.solicitudesDeApertura = new ArrayList<>();
    this.incidentes = new ArrayList<>();
  }

  public void ingresarViandas(List<Vianda> viandas) {
    this.viandas.addAll(viandas);
  }

  public void quitarViandas(List<Vianda> viandas) {
    viandas.forEach(this.viandas::remove);
  }

  private int calcularMesesActiva() {
    int mesesActiva = 0;
    LocalDate fechaInicio = fechaRegistro.toLocalDate();

    for (CambioEstado cambio : historialEstados) {
      if (cambio.getEstado() == EstadoHeladera.ACTIVA && fechaInicio.isBefore(cambio.getFechaCambio())) {
        mesesActiva += Period.between(LocalDate.from(fechaInicio), cambio.getFechaCambio()).getMonths();
        fechaInicio = cambio.getFechaCambio();
      } else if (cambio.getEstado() != EstadoHeladera.ACTIVA && fechaInicio.isBefore(cambio.getFechaCambio())) {
        fechaInicio = cambio.getFechaCambio();
      }
    }

    if (estado == EstadoHeladera.ACTIVA) {
      mesesActiva += Period.between(LocalDate.from(fechaInicio), LocalDate.now()).getMonths();
    }

    return mesesActiva;
  }

  public float calcularPuntaje() {
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteCantidadHeladerasActivas");
    return coeficiente * this.calcularMesesActiva();
  }

  public TipoContribucion obtenerTipoContribucion() {
    return TipoContribucion.ENCARGARSE_DE_HELADERA;
  }

  public LocalDate obtenerFechaRegistro() {
    return this.fechaRegistro.toLocalDate();
  }

  public void cambiarEstado(EstadoHeladera nuevoEstado) {
    if (this.estado != nuevoEstado) {
      this.estado = nuevoEstado;
      this.agregarCambioDeEstado(new CambioEstado(nuevoEstado, LocalDate.now()));
    }
  }

  private void agregarCambioDeEstado(CambioEstado cambioEstado) {
    this.historialEstados.add(cambioEstado);
  }

  private boolean tieneTemperaturaEnRango(Float temperatura) {
    return temperatura >= modelo.getTemperaturaMinima() && temperatura <= modelo.getTemperaturaMaxima();
  }

  public void cambiarTemperatura(float nuevaTemperatura) {
    if (!tieneTemperaturaEnRango(nuevaTemperatura)) {
      this.cambiarEstado(EstadoHeladera.FALLA_TEMPERATURA);
    }

    agregarTemperaturaAlHistorial(new CambioTemperatura(LocalDateTime.now(), nuevaTemperatura));
  }

  public void agregarTemperaturaAlHistorial(CambioTemperatura temperatura) {
    this.historialTemperaturas.add(temperatura);
  }

  public boolean estaActiva() {
    return this.estado == EstadoHeladera.ACTIVA;
  }

  public boolean validarApertura(String codigoTarjeta) {
    return this.solicitudesDeApertura.stream().anyMatch(sol -> sol.esValida(codigoTarjeta));
  }

  public void agregarSolicitudApertura(SolicitudApertura solicitud) {
    if (!this.estaActiva()) {
      throw new HeladeraInactivaException();
    }

    this.solicitudesDeApertura.add(solicitud);
    PublicadorSolicitudApertura
        .getInstance()
        .publicarSolicitudApertura(solicitud.getCodigoTarjeta(), solicitud.getFecha(), this.id);
  }

  public int cantidadViandas() {
    return this.viandas.size();
  }

  public int cantidadViandasVirtuales() {
    return this.cantidadViandas() + this.cantidadViandasIngresadasVirtualmente() - this.cantidadViandasQuitadasVirtualmente();
  }

  public int cantidadViandasQuitadasVirtualmente() {
    Stream<SolicitudApertura> quitadas = this.solicitudesDeApertura.stream().filter(SolicitudApertura::esQuitadaVirtualmente);
    return quitadas.mapToInt(SolicitudApertura::getCantidadViandas).sum();
  }

  public int cantidadViandasIngresadasVirtualmente() {
    Stream<SolicitudApertura> ingresadas = this.solicitudesDeApertura.stream().filter(SolicitudApertura::esIngresadaVirtualmente);
    return ingresadas.mapToInt(SolicitudApertura::getCantidadViandas).sum();
  }

  public void recibirAlertaFraude() {
    this.setEstado(EstadoHeladera.FRAUDE);
    this.agregarCambioDeEstado(new CambioEstado(EstadoHeladera.FRAUDE, LocalDate.now()));
  }

  public void quitarVianda(Vianda vianda) {
    this.viandas.remove(vianda);
  }

  public void ingresarVianda(Vianda vianda) { this.viandas.add(vianda); }
}