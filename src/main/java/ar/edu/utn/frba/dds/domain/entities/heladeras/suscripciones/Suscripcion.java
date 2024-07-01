package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;

public abstract class Suscripcion {
  private PersonaHumana colaborador;
  public void notificar(Heladera heladera) {
    if (cumpleCondicion(heladera)) {
      Mensaje mensaje = armarMensaje(heladera);
      this.colaborador.serNotificadoPor(mensaje);
    }
  }

  protected abstract boolean cumpleCondicion(Heladera heladera);

  protected abstract Mensaje armarMensaje(Heladera heladera);
}
