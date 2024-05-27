package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorMovimiento;
import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorTemperatura;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
    this.estado = estado;
    this.temperaturaEsperada = temperaturaEsperada;
    this.viandas = new HashSet<>();
  }

  public void ingresarViandas(Vianda ...viandas) {
    Collections.addAll(this.viandas, viandas);
  }

  public void quitarViandas(Vianda ...viandas) {
    Arrays.stream(viandas).toList().forEach(this.viandas::remove);
  }

  public float temperaturaReal() {
    //TODO: Llamar al adapter de temperatura
    return 0;
  }

  public float calcularPuntaje() {
    // TODO:
    return 0;
  }

  public void recalcularEstado() {
    // TODO:
  }

}
