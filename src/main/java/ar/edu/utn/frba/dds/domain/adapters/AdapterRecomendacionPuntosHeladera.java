package ar.edu.utn.frba.dds.domain.adapters;

import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface AdapterRecomendacionPuntosHeladera {
  public List<Coordenada> recomendacion(String latitud, String longitud, float radio) throws IOException;
}
