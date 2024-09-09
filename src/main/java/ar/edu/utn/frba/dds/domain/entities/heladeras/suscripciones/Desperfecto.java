package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.GeoRefServicio;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("DESPERFECTO")
@NoArgsConstructor
public class Desperfecto extends Suscripcion {
  @Transient
  private boolean aceptado;


  private List<SugerenciaHeladera> sugerencias;

  @Transient
  private IRepositorioHeladera repositorioHeladeras;

  public Desperfecto(IObserverNotificacion suscriptor) {
    this.suscriptor = suscriptor;
  }

  protected boolean cumpleCondicion(Heladera heladera) {
    return !heladera.estaActiva();
  }

  protected String armarCuerpo(Heladera heladera) {
    return "La heladera " + heladera.getNombre() + " sufrió un desperfecto y las viandas "
        + "deben ser llevadas a otras heladeras a la brevedad para que las mismas no se "
        + "echen a perder.\n"
        + "Le sugerimos estas llevar las viandas a estas heladeras:\n"
        + this.sugerirHeladeras(heladera);
  }

  private String sugerirHeladeras(Heladera heladera) {
    List<String> heladerasRecomendadas = repositorioHeladeras.recomendarHeladeras(heladera.getDireccion());

    String heladerasSugeridas = "";
    for (String nombreHeladera : heladerasRecomendadas) {
      heladerasSugeridas = heladerasSugeridas.concat("   - " + nombreHeladera + "\n");
    }

    
    sugerencias.add(s)
    return heladerasSugeridas;
  }
}
