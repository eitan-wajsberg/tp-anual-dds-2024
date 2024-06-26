package ar.edu.utn.frba.dds.domain.entities.usuarios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permiso {
  private String nombre;

  public Permiso(String nombre) {
    this.nombre = nombre;
  }
}
