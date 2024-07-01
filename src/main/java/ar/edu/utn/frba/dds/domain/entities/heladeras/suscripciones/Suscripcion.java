package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.time.LocalDateTime;

public abstract class Suscripcion {
  private PersonaHumana colaborador;

  public void notificar(Heladera heladera) {
    if (cumpleCondicion(heladera)) {
      Mensaje mensaje = armarMensaje(heladera);
      this.colaborador.serNotificadoPor(mensaje);
    }
  }

  private Mensaje armarMensaje(Heladera heladera) {
    return new Mensaje("SMAACVS: Notificacion sobre " + heladera.getNombre(),
        "Estimado colaborador,\n"
            + this.armarCuerpo(heladera) + "\n"
            + "Saludos, \n\n"
            + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica",
        LocalDateTime.now()
    );
  }

  protected abstract boolean cumpleCondicion(Heladera heladera);

  protected abstract String armarCuerpo(Heladera heladera);
}
