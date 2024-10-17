package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.github.jknack.handlebars.helper.ConditionalHelpers.and;

@Getter @Setter
@Entity
@Table(name="suscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipoSuscripcion")

public abstract class Suscripcion {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_persona_humana", referencedColumnName = "id", nullable = false)
  protected PersonaHumana suscriptor;

  @ManyToOne
  @JoinColumn(name = "id_heladera", referencedColumnName = "id", nullable = false)
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

//  public abstract Suscripcion fromDTO(SuscripcionDTO dto);

public static Suscripcion fromDTO(SuscripcionDTO dto) {

  validarCamposObligatorios(dto);
  validarValoresNumericos(dto);

  try {
    // Buscar el suscriptor y la heladera a partir del DTO
    PersonaHumana suscriptor = ServiceLocator.instanceOf(RepositorioPersonaHumana.class)
            .buscarPorId(dto.getIdPersonaHumana(), PersonaHumana.class)
            .orElseThrow(() -> new IllegalArgumentException("Persona Humana no encontrada"));

    Heladera heladera = ServiceLocator.instanceOf(RepositorioHeladera.class)
            .buscarPorId(dto.getIdHeladera(), Heladera.class)
            .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrada"));

    // Condicional según el tipo de suscripción
    if ("FALTAN_N_VIANDAS".equals(dto.getTipoSuscripcion())) {
      FaltanNViandas faltanNViandas = new FaltanNViandas();
      faltanNViandas.setSuscriptor(suscriptor);
      faltanNViandas.setHeladera(heladera);
      return faltanNViandas;

    } else if ("QUEDAN_N_VIANDAS".equals(dto.getTipoSuscripcion())) {
      QuedanNViandas quedanNViandas = new QuedanNViandas();
      quedanNViandas.setSuscriptor(suscriptor);
      quedanNViandas.setHeladera(heladera);
      return quedanNViandas;

    } else if ("DESPERFECTO".equals(dto.getTipoSuscripcion())) {
      Desperfecto desperfecto = new Desperfecto();
      desperfecto.setSuscriptor(suscriptor);
      desperfecto.setHeladera(heladera);
      return desperfecto;

    } else {
      throw new IllegalArgumentException("Tipo de suscripción no reconocido.");
    }
  } catch (IllegalArgumentException e) {
    throw new IllegalStateException("La suscripción debe tener un suscriptor y una heladera.", e);
  }
}


    // Validar los campos obligatorios
  static void validarCamposObligatorios(SuscripcionDTO dto) {
    if (dto.getIdPersonaHumana() == null || dto.getIdHeladera() == null) {
      throw new IllegalArgumentException("La persona humana y la heladera son campos obligatorios.");
    }
  }

  // Validar que los valores numéricos sean válidos
  static void validarValoresNumericos(SuscripcionDTO dto) {
    if (dto.getCantidadViandasFaltantes() != null && dto.getCantidadViandasFaltantes() < 0  ) {
      throw new IllegalArgumentException("La cantidad de viandas faltantes no puede ser negativa.");
    }
    if (dto.getCantidadViandasQueQuedan() != null &&dto.getCantidadViandasQueQuedan() < 0) {
      throw new IllegalArgumentException("La cantidad de viandas que quedan no puede ser negativa.");
    }
  }

}
