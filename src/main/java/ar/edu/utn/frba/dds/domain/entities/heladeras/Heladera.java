package ar.edu.utn.frba.dds.domain.entities.heladeras;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.AccionApertura;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.SolicitudApertura;
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
  private EstadoHeladera estado;
  @Setter
  private float temperaturaEsperada;
  private List<CambioEstado> historialEstados;
  private List<CambioTemperatura> historialTemperaturas;
  private List<SolicitudApertura> solicitudesDeApertura;
  private GestorSuscripciones gestorSuscripciones;

  public Heladera() {
    this.viandas = new HashSet<>();
    this.historialEstados = new ArrayList<>();
    this.estado = EstadoHeladera.ACTIVA;
    this.historialTemperaturas = new ArrayList<>();
    this.solicitudesDeApertura = new ArrayList<>();
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

  public void cambiarEstado(EstadoHeladera nuevoEstado) {
    if (this.estado != nuevoEstado) {
      this.estado = nuevoEstado;
      this.agregarCambioDeEstado(new CambioEstado(nuevoEstado, LocalDate.now()));
    }
  }

  private void agregarCambioDeEstado(CambioEstado cambioEstado) {
    this.historialEstados.add(cambioEstado);
  }

  private boolean tieneTemperaturaValida() {
    return !this.noSuperaTemperaturaMinima() && !this.superaTemperaturaMaxima();
  }

  private boolean superaTemperaturaMaxima() {
    List<CambioTemperatura> historial = this.historialTemperaturas;
    return historial.get(historial.size() - 1).getTemperaturaCelsius() > modelo.getTemperaturaMaxima();
  }

  private boolean noSuperaTemperaturaMinima() {
    List<CambioTemperatura> historial = this.historialTemperaturas;
    return historial.get(historial.size() - 1).getTemperaturaCelsius() < modelo.getTemperaturaMinima();
  }

  public void cambiarTemperatura(float nuevaTemperatura) {
    // FIXME: Este metodo estaba dirigido a cambiar la temperatura de la heladera fisica?
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
    this.solicitudesDeApertura.add(solicitud);
  }

  public int cantidadViandas() {
    return this.viandas.size();
  }

  public int cantidadViandasVirtuales() {
    return this.cantidadViandas() + this.cantidadViandasIngresadasVirtualmente() - this.cantidadViandasQuitadasVirtualmente();
  }

  private int cantidadViandasQuitadasVirtualmente() {
    Stream<SolicitudApertura> quitadas = this.solicitudesDeApertura.stream().filter(SolicitudApertura::esQuitadaVirtualmente);
    return quitadas.mapToInt(SolicitudApertura::getCantidadViandas).sum();
  }

  private int cantidadViandasIngresadasVirtualmente() {
    Stream<SolicitudApertura> ingresadas = this.solicitudesDeApertura.stream().filter(SolicitudApertura::esIngresadaVirtualmente);
    return ingresadas.mapToInt(SolicitudApertura::getCantidadViandas).sum();
  }

  public void enviarAHeladeraFisicaSolicitudApertura() {
    // TODO
  }

  public void quitarVianda(Vianda vianda) {
    this.viandas.remove(vianda);
  }

  public void ingresarVianda(Vianda vianda) {
    this.viandas.add(vianda);
  }
}