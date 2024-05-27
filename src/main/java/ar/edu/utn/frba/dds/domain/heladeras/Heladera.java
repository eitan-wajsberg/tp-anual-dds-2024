package ar.edu.utn.frba.dds.domain.heladeras;

import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorMovimiento;
import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorTemperatura;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import java.time.LocalDateTime;
import java.util.Set;

public class Heladera {
  private String nombre;
  private Direccion direccion;
  private LocalDateTime fechaRegistro;
  private int capacidadMaximaViandas;
  private boolean activa;
  private Modelo modelo;
  private Set<Vianda> viandas;
  private EstadoHeladera estado;
  private float temperaturaEsperada;
  private AdapterSensorTemperatura adapterTemperatura;
  private AdapterSensorMovimiento adapterSensorMovimiento;


  public void ingresarViandas(int cantidad) {
    //TODO:
  }

  public void quitarViandas(int cantidad) {
    //TODO:
  }

  public float temperatura() {
    //TODO:
    return 0;
  }

}
