package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Direccion {
  private Calle calle;
  private String altura;
  private Municipio municipio;
  private Provincia provincia;

  public boolean estaCercaDe() {
    // TODO
    return false;
  }

  public Coordenada obtenerCoordenada() {
    // TODO
    return null;
  }
}
