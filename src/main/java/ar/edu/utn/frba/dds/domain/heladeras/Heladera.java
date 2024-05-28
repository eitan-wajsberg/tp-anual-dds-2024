package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorMovimiento;
import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorTemperatura;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  private boolean activa;
  @Setter
  private Modelo modelo;
  private Set<Vianda> viandas;
  @Setter
  private EstadoHeladera estado;
  @Setter
  private float temperaturaEsperada;
  private AdapterSensorTemperatura adapterTemperatura;
  private AdapterSensorMovimiento adapterSensorMovimiento;
  private List<CambioEstado> historialEstados;


  public Heladera() {
    this.viandas = new HashSet<>();
    this.historialEstados = new ArrayList<>();
  }

  public void ingresarViandas(List<Vianda> viandas) {
    this.viandas.addAll(viandas);
  }

  public void quitarViandas(List<Vianda> viandas) {
    viandas.forEach(this.viandas::remove);
  }

  public float temperaturaReal() {
    return adapterTemperatura.detectarTemperatura();
  }

  public void setEstado(EstadoHeladera nuevoEstado) {
    if (this.estado != nuevoEstado) {
      this.estado = nuevoEstado;
      this.historialEstados.add(new CambioEstado(nuevoEstado, LocalDate.now()));
    }
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

  public void recalcularEstado() {
    if(adapterSensorMovimiento.detectarFraude())
      setEstado(EstadoHeladera.FRAUDE);
    else if (this.noSuperaTemperaturaMinima() || this.superaTemperaturaMaxima())
      setEstado(EstadoHeladera.DESPERFECTO);
    else
      setEstado(EstadoHeladera.ACTIVA);
  }

  public boolean superaTemperaturaMaxima() {
    return adapterTemperatura.detectarTemperatura() > modelo.getTemperaturaMaxima();
  }

  public boolean noSuperaTemperaturaMinima() {
    return adapterTemperatura.detectarTemperatura() < modelo.getTemperaturaMinima();
  }

  public float calcularPuntaje() {
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return coeficientes.get("coeficienteCantidadHeladerasActivas") * this.calcularMesesActiva();
  }

  public void agregarCambioDeEstado(CambioEstado cambioEstado) {
    this.historialEstados.add(cambioEstado);
  }
}