package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import lombok.Builder;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FALTAN_N_VIANDAS")
public class FaltanNViandas extends Suscripcion {
  @Column(name="cantidadViandasFaltantes")
  @Setter
  private int cantidadViandasParaLlenarse;

  public FaltanNViandas() {

  }
  protected boolean cumpleCondicion(Heladera heladera) {
    return heladera.cantidadViandasVirtuales() + cantidadViandasParaLlenarse == heladera.getCapacidadMaximaViandas();
  }

  protected String armarCuerpo(Heladera heladera) {
    return "Faltan " + cantidadViandasParaLlenarse
        + " viandas para que la heladera "
        + heladera.getNombre() + " este llena.";
  }

//  @Override
//  public Suscripcion fromDTO(SuscripcionDTO dto) {
//
//    validarCamposObligatorios(dto);
//    validarValoresNumericos(dto);
//    FaltanNViandas suscripcion = new FaltanNViandas();
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
