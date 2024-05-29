package ar.edu.utn.frba.dds.domain.puntosRecomendados;

import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import java.util.List;
import lombok.Getter;

@Getter
public class ListadoPuntosRecomendados {
  private class Parametros {
    public String latitud;
    public String longitud;
    public float radio;
  }
  public List<Coordenada> puntos;
}
