package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorPersonaVulnerable implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private Repositorio repositorioPersonaVulnerable;
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private final String rutaRegistroHbs = PrettyProperties.getInstance().propertyFromName("hbs_registro_persona_vulnerable");
  private final String rutaListadoHbs = PrettyProperties.getInstance().propertyFromName("hbs_listado_personas_vulnerables");

  public ControladorPersonaVulnerable(Repositorio repositorioPersonaVulnerable, RepositorioPersonaHumana repositorioPersonaHumana) {
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }

  @Override
  public void index(Context context) {
    List<PersonaVulnerable> vulnerables = this.repositorioPersonaVulnerable.buscarTodos(PersonaVulnerable.class);

    Map<String, Object> model = new HashMap<>();
    model.put("personasVulnerables", vulnerables);

    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    // TODO
  }

  @Override
  public void create(Context context) {
    context.render(rutaRegistroHbs);
  }

  @Override
  public void save(Context context) {
    PersonaVulnerableDTO dto = new PersonaVulnerableDTO();
    dto.obtenerFormulario(context);

    try {
      Optional<PersonaHumana> registrador = repositorioPersonaHumana.buscarPorId(1L, PersonaHumana.class);
      if (registrador.isEmpty()) {
        throw new ValidacionFormularioException("No se ha encontrado la persona que lo está registrando. Reintentar.");
      }

      PersonaVulnerable nuevaPersona = PersonaVulnerable.fromDTO(dto);
      if (nuevaPersona == null) {
        throw new ValidacionFormularioException("Se ha ingresado información incorrecta sobre la persona vulnerable.");
      }

      nuevaPersona.setPersonaQueLoRegistro(registrador.get());
      nuevaPersona.setFechaDeRegistro(LocalDate.now());

      withTransaction(() -> repositorioPersonaVulnerable.guardar(nuevaPersona));

      context.redirect(rutaListadoHbs);
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      context.render(rutaRegistroHbs, model);
    }
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