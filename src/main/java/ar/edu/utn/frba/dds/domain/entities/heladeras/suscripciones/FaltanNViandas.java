package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.time.LocalDateTime;

public class FaltanNViandas extends Suscripcion {
  private int cantidadViandasParaLlenarse;

  public FaltanNViandas(IObserverNotificacion suscriptor, int cantidadViandasParaLlenarse) {
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
