package ar.edu.utn.frba.dds.domain.definirPackages;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class Vianda {

  private Heladera heladera;
  private LocalDateTime fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float calorias;
  private float peso;
  private PersonaHumana colaborador;
  private LocalDateTime fechaDonacion;

  public void moverA(Heladera heladera) {

  }

  public void donar() {

  }

  public void setEntregada(boolean entregada) { // boolean entregada ?

  }

}
