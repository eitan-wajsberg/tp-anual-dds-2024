package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FALTAN_N_VIANDAS")
public class FaltanNViandas extends Suscripcion {
  @Column(name="cantidadViandasFaltantes")
  private int cantidadViandasParaLlenarse;

  public FaltanNViandas(PersonaHumana suscriptor, int cantidadViandasParaLlenarse) {
    this.cantidadViandasParaLlenarse = cantidadViandasParaLlenarse;
    this.suscriptor = suscriptor;
  }

  protected boolean cumpleCondicion(Heladera heladera) {
    return heladera.cantidadViandasVirtuales() + cantidadViandasParaLlenarse == heladera.getCapacidadMaximaViandas();
  }

  protected String armarCuerpo(Heladera heladera) {
    return "Faltan " + cantidadViandasParaLlenarse
        + " viandas para que la heladera "
        + heladera.getNombre() + " este llena.";
  }
}
