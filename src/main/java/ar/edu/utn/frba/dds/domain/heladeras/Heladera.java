package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorMovimiento;
import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorTemperatura;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class Heladera {
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

  public float calcularPuntaje() {
    // TODO: Hay que obtenerlo del archivo de configuracion supongo
    return 0;
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

}
