package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.List;

public class Desperfecto extends Suscripcion {
  private boolean aceptado;

  protected boolean cumpleCondicion(Heladera heladera) {
    return !heladera.estaActiva();
  }

  protected String armarCuerpo(Heladera heladera) {
    return "La heladera " + heladera.getNombre() + " sufrió un desperfecto y las viandas "
        + "deben ser llevadas a otras heladeras a la brevedad para que las mismas no se "
        + "echen a perder.\n"
        + "Le sugerimos estas llevar las viandas a estas heladeras:\n"
        + this.sugerirHeladeras(heladera);
  }

  private String sugerirHeladeras(Heladera heladera) {
    // TODO
    return null;
  }
}
