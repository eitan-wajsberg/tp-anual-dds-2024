package ar.edu.utn.frba.dds.domain.definirPackages;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class Heladera {
  private String nombre;
  private Direccion direccion;
  private String latitud;
  private String longitud;
  private Integer cantidadViandas;
  private LocalDateTime fechaRegistro;
  private int capacidadMaximaViandas;
  private boolean activa;
  private Modelo modelo;

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
