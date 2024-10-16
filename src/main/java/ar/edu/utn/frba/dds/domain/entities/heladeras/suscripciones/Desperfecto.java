package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;

import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@DiscriminatorValue("DESPERFECTO")
@NoArgsConstructor
public class Desperfecto extends Suscripcion {

  @Transient
  private boolean aceptado;

  @OneToMany
  @JoinColumn(name = "suscripcion_id", referencedColumnName = "id")
  private List<SugerenciaHeladera> sugerencias;

  @Transient
  private RepositorioHeladera repositorioHeladeras; // TODO: REVISAR repositorio

  public Desperfecto(PersonaHumana suscriptor) {
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

  private String sugerirHeladeras(Heladera heladeraConDesperfecto) {
    List<Heladera> heladerasRecomendadas = repositorioHeladeras.recomendarHeladeras(heladeraConDesperfecto.getDireccion());

    SugerenciaHeladera sugerencia = new SugerenciaHeladera();
    String heladerasSugeridas = "";
    for (Heladera heladera : heladerasRecomendadas) {
      heladerasSugeridas = heladerasSugeridas.concat("   - " + heladera.getNombre() + "\n");
      sugerencia.agregarHeladera(heladera);
    }
    sugerencias.add(sugerencia);

    return heladerasSugeridas;
  }
//
//  @Override
//  public Suscripcion fromDTO(SuscripcionDTO dto) {
//
//    validarCamposObligatorios(dto);
//    validarValoresNumericos(dto);
//    Desperfecto suscripcion = new Desperfecto();
//
//    try {
//      PersonaHumana suscriptor = ServiceLocator.instanceOf(RepositorioPersonaHumana.class).buscarPorId(dto.getIdPersonaHumana(), PersonaHumana.class).get();
//      Heladera heladera = ServiceLocator.instanceOf(RepositorioHeladera.class).buscarPorId(dto.getIdHeladera(),Heladera.class).get();
//      // Crear la instancia de Suscripcion
//
//      suscripcion.setSuscriptor(suscriptor);
//      suscripcion.setHeladera(heladera);
//    }
//    catch (IllegalArgumentException e){
//      throw new IllegalStateException("La suscripción debe tener un suscriptor y una heladera.");
//    }
//    return suscripcion;
//  }
}
