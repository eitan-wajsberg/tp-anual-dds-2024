package ar.edu.utn.frba.dds.domain.ubicacion;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordenada {
  public String latitud;
  public String longitud;

  public Coordenada(String latitud, String longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordenada that = (Coordenada) o;
    return Objects.equals(latitud, that.latitud) &&
        Objects.equals(longitud, that.longitud);
  }
}

