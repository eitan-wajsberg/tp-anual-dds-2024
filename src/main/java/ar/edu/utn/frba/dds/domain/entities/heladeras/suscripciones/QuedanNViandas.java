package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("QUEDAN_N_VIANDAS")
@NoArgsConstructor
public class QuedanNViandas extends Suscripcion {
  @Getter
  @Column(name="cantidadViandasQueQuedan")
  private int cantidadViandasDisponibles;

  public QuedanNViandas(PersonaHumana suscriptor, int cantidadViandasDisponibles) {
    this.cantidadViandasDisponibles = cantidadViandasDisponibles;
    this.suscriptor = suscriptor;
  }

  protected boolean cumpleCondicion(Heladera heladera) {
    return heladera.cantidadViandasVirtuales() == cantidadViandasDisponibles;
  }

  protected String armarCuerpo(Heladera heladera) {
    return "Quedan únicamente " + cantidadViandasDisponibles
        + " viandas disponibles en la heladera "
        + heladera.getNombre() + ".";
  }
}
