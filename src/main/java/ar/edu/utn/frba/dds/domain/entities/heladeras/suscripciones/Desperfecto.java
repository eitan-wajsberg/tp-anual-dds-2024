package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;

public class Desperfecto extends Suscripcion{
  @Override
  protected boolean cumpleCondicion(Heladera heladera) {
    return false;
  }

  @Override
  protected Mensaje armarMensaje(Heladera heladera) {
    return null;
  }
}
