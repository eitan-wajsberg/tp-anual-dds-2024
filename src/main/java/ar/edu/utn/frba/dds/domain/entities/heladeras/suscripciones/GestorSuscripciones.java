package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.Map;

public class GestorSuscripciones {
  private Map<TipoContribucion, Suscripcion> suscripcionesPorTipo;

  public void notificar(TipoSuscripcion tipo, Heladera heladera) {

  }
}
