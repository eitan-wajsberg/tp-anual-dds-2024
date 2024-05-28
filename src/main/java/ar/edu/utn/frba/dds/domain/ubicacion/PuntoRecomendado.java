package ar.edu.utn.frba.dds.domain.ubicacion;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PuntoRecomendado {
  public Coordenada coordenada;
  public float radio;

  public PuntoRecomendado(Coordenada coordenada, float radio) {
    this.coordenada = coordenada;
    this.radio = radio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PuntoRecomendado that = (PuntoRecomendado) o;
    return Float.compare(that.radio, radio) == 0 &&
        Objects.equals(coordenada, that.coordenada);
  }
}
