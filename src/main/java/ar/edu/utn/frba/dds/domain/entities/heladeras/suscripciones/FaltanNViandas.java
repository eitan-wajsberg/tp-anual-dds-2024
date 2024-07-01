package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;

public class FaltanNViandas extends Suscripcion {
  private int cantidadViandasParaLlenarse;
  private PersonaHumana colaborador;

  protected boolean cumpleCondicion(Heladera heladera) {
    return false;
  }

  protected Mensaje armarMensaje(Heladera heladera) {
    return null;
  }
}
