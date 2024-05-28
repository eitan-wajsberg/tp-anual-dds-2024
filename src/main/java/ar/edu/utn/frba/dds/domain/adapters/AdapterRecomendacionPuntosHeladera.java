package ar.edu.utn.frba.dds.domain.adapters;

import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import java.util.Set;

public interface AdapterRecomendacionPuntosHeladera {
  public Set<Coordenada> recomendacion(String latitud, String longitud, float radio);
}
