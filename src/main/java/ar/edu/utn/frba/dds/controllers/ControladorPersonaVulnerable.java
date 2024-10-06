package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.DireccionDTO;
import ar.edu.utn.frba.dds.dtos.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ControladorPersonaVulnerable implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private Repositorio repositorioPersonaVulnerable;
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("hbs_registro_persona_vulnerable");

  public ControladorPersonaVulnerable(Repositorio repositorioPersonaVulnerable, RepositorioPersonaHumana repositorioPersonaHumana) {
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }

  @Override
  public void index(Context context) {
    // TODO
  }

  @Override
  public void show(Context context) {
    // TODO
  }

  @Override
  public void create(Context context) {
    context.render(rutaHbs);
  }

  @Override
  public void save(Context context) {
    PersonaVulnerableDTO dto = new PersonaVulnerableDTO();
    dto.obtenerFormulario(context, rutaHbs);
    PersonaVulnerable nuevaPersona = (PersonaVulnerable) dto.convertirAEntidad();

    if (nuevaPersona == null) {
      throw new ValidacionFormularioException("Se ha ingresado información incorrecta sobre la persona vulnerable", rutaHbs);
    }

    // FIXME: obtener de alguna forma de la sesion el id del registrador
    Optional<PersonaHumana> registrador = repositorioPersonaHumana.buscarPorId(1L, PersonaHumana.class);
    if (registrador.isEmpty()) {
      throw new ValidacionFormularioException("No se ha encontrado la persona que lo está registrando. Reintentar.", rutaHbs);
    }

    nuevaPersona.setPersonaQueLoRegistro(registrador.get());
    nuevaPersona.setFechaDeRegistro(LocalDate.now());

    withTransaction(() -> repositorioPersonaVulnerable.guardar(nuevaPersona));
    context.redirect("/inicio");
  }


  @Override
  public void edit(Context context) {
    // TODO
  }

  @Override
  public void update(Context context) {
    // TODO
  }

  @Override
  public void delete(Context context) {
    // TODO
  }
}