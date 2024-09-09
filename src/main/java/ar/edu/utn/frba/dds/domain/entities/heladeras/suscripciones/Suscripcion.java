package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;

import java.time.LocalDateTime;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="suscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoSuscripcion")
public abstract class Suscripcion {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_persona_humana", referencedColumnName = "id")
  protected IObserverNotificacion suscriptor;

  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  private Heladera heladera;

  public void notificar(Heladera heladera) {
    if (cumpleCondicion(heladera)) {
      Mensaje mensaje = armarMensaje(heladera);
      this.suscriptor.serNotificadoPor(mensaje);
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
