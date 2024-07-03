package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.time.LocalDateTime;

public class FaltanNViandas extends Suscripcion {
  private int cantidadViandasParaLlenarse;

  public FaltanNViandas(PersonaHumana colaborador, int cantidadViandasParaLlenarse) {
    this.cantidadViandasParaLlenarse = cantidadViandasParaLlenarse;
    this.colaborador = colaborador;
  }

  protected boolean cumpleCondicion(Heladera heladera) {
    return heladera.cantidadViandas() + cantidadViandasParaLlenarse == heladera.getCapacidadMaximaViandas();
  }

  protected String armarCuerpo(Heladera heladera) {
    return "Faltan " + cantidadViandasParaLlenarse
        + " viandas para que la heladera "
        + heladera.getNombre() + " este llena.";
  }
}
