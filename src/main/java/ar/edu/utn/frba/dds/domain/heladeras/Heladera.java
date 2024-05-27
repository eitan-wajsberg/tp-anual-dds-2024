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

public class Heladera implements Contribucion {
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private Direccion direccion;
  @Getter @Setter
  private LocalDateTime fechaRegistro;
  @Getter @Setter
  private int capacidadMaximaViandas;
  @Getter @Setter
  private boolean activa;
  @Getter @Setter
  private Modelo modelo;
  @Getter
  private Set<Vianda> viandas;
  @Getter @Setter
  private EstadoHeladera estado;
  @Getter @Setter
  private float temperaturaEsperada;
  private AdapterSensorTemperatura adapterTemperatura;
  private AdapterSensorMovimiento adapterSensorMovimiento;
  private List<CambioEstado> historialEstados;


  public Heladera(String nombre, Direccion direccion, LocalDateTime fechaRegistro, int capacidadMaximaViandas, boolean activa, Modelo modelo, EstadoHeladera estado, float temperaturaEsperada) {
    this.nombre = nombre;
    this.direccion = direccion;
    this.fechaRegistro = fechaRegistro;
    this.capacidadMaximaViandas = capacidadMaximaViandas;
    this.activa = activa;
    this.modelo = modelo;
    this.estado = EstadoHeladera.ACTIVA; // Se inicializa activa??
    this.temperaturaEsperada = temperaturaEsperada;
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
    LocalDateTime fechaInicio = fechaRegistro;

    for (CambioEstado cambio : historialEstados) {
      if (cambio.getEstado() == EstadoHeladera.ACTIVA && fechaInicio.isBefore(cambio.getFechaCambio().atStartOfDay())) {
        mesesActiva += Period.between(LocalDate.from(fechaInicio), cambio.getFechaCambio()).getMonths();
        fechaInicio = cambio.getFechaCambio().atStartOfDay();
      } else if (cambio.getEstado() != EstadoHeladera.ACTIVA && fechaInicio.isBefore(cambio.getFechaCambio().atStartOfDay())) {
        fechaInicio = cambio.getFechaCambio().atStartOfDay();
      }
    }

    // Consider the current period if the current state is active
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
    return coeficientes.get("HELADERA_A_CARGO") * this.calcularMesesActiva();
  }
}