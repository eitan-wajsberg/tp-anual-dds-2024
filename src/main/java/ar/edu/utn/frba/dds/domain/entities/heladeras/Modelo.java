package ar.edu.utn.frba.dds.domain.entities.heladeras;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Modelo {
  private String modelo;
  private float temperaturaMinima;
  private float temperaturaMaxima;

  public Modelo(String modelo, float temperaturaMinima, float temperaturaMaxima) {
    this.modelo = modelo;
    this.temperaturaMinima = temperaturaMinima;
    this.temperaturaMaxima = temperaturaMaxima;
  }
}
