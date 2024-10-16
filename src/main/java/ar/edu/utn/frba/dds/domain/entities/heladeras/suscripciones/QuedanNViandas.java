package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("QUEDAN_N_VIANDAS")
@NoArgsConstructor
public class QuedanNViandas extends Suscripcion {
  @Getter @Setter
  @Column(name="cantidadViandasQueQuedan")
  private int cantidadViandasDisponibles;

  protected boolean cumpleCondicion(Heladera heladera) {
    return heladera.cantidadViandasVirtuales() == cantidadViandasDisponibles;
  }

  protected String armarCuerpo(Heladera heladera) {
    return "Quedan únicamente " + cantidadViandasDisponibles
        + " viandas disponibles en la heladera "
        + heladera.getNombre() + ".";
  }

//  @Override
//  public Suscripcion fromDTO(SuscripcionDTO dto) {
//
//    validarCamposObligatorios(dto);
//    validarValoresNumericos(dto);
//    QuedanNViandas suscripcion = new QuedanNViandas();
//
//    try {
//      PersonaHumana suscriptor = ServiceLocator.instanceOf(RepositorioPersonaHumana.class).buscarPorId(dto.getIdPersonaHumana(), PersonaHumana.class).get();
//      Heladera heladera = ServiceLocator.instanceOf(RepositorioHeladera.class).buscarPorId(dto.getIdHeladera(),Heladera.class).get();
//      // Crear la instancia de Suscripcion
//
//      suscripcion.setSuscriptor(suscriptor);
//      suscripcion.setHeladera(heladera);
//      suscripcion.setCantidadViandasDisponibles(dto.getCantidadViandasQueQuedan());
//    }
//    catch (IllegalArgumentException e){
//      throw new IllegalStateException("La suscripción debe tener un suscriptor y una heladera.");
//    }
//    return suscripcion;
//  }

}
