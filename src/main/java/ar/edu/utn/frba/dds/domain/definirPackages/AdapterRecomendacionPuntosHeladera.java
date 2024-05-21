package ar.edu.utn.frba.dds.domain.definirPackages;

import java.util.Set;

public interface AdapterRecomendacionPuntosHeladera {

  public Set<Coordenada> recomendacion(String latitud, String longitud, float radio);

}
