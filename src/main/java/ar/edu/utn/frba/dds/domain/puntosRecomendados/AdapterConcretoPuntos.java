package ar.edu.utn.frba.dds.domain.puntosRecomendados;

import ar.edu.utn.frba.dds.domain.adapters.AdapterRecomendacionPuntosHeladera;
import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import java.io.IOException;
import java.util.List;
import lombok.Setter;

@Setter
public class AdapterConcretoPuntos implements AdapterRecomendacionPuntosHeladera {

  private ServicioRecomendacionPuntos servicioRecomendacionPuntos;

  @Override
  public List<Coordenada> recomendacion(String latitud, String longitud, float radio) throws IOException {
    return servicioRecomendacionPuntos.listadoPuntosRecomendados(radio, latitud, longitud).puntos;
  }
}
