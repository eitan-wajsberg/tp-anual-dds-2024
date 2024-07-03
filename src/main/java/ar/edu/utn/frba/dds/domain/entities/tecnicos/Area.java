package ar.edu.utn.frba.dds.domain.entities.tecnicos;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Area {
  private Coordenada coordenada;
  private float radio;

  public Area(Coordenada coordenada, float radio) {
    this.coordenada = coordenada;
    this.radio = radio;
  }
}
