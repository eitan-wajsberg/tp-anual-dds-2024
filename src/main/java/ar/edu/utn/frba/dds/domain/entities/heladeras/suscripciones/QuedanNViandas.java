package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import lombok.Getter;

import java.time.LocalDateTime;

public class QuedanNViandas extends Suscripcion {
  @Getter
  private int cantidadViandasDisponibles;

  public QuedanNViandas(PersonaHumana colaborador, int cantidadViandasDisponibles) {
    this.cantidadViandasDisponibles = cantidadViandasDisponibles;
    this.colaborador = colaborador;
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
