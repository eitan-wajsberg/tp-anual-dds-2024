package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.PersonaHumanaDTO;
import ar.edu.utn.frba.dds.dtos.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorPersonaHumana implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private final String rutaPersonaHbs = "cuenta/formularioPersonaHumana.hbs";
  private final String rutaPantallaPrincipal = "/";
  private final String ERROR = "error";

  public ControladorPersonaHumana(RepositorioPersonaHumana repositorioPersonaHumana) {
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }

  @Override
  public void index(Context context) {
    // There's nothing here
  }

  @Override
  public void show(Context context) {
    // There's nothing here, as well
  }

  public void create(Context context) {
    context.render(rutaPersonaHbs);
  }

  @Override
  public void save(Context context) {
    PersonaHumanaDTO dto = new PersonaHumanaDTO();
    dto.obtenerFormulario(context);

    try {
      PersonaHumana nuevaPersona = PersonaHumana.fromDTO(dto);
      if (nuevaPersona == null) {
        throw new ValidacionFormularioException("Se han ingresado datos incorrectos.");
      }
      withTransaction(() -> repositorioPersonaHumana.guardar(nuevaPersona));
      context.redirect(rutaPantallaPrincipal);

    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put(ERROR, e.getMessage());
      model.put("dto", dto);
      context.render(rutaPersonaHbs, model);
    }
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<PersonaHumana> persona = repositorioPersonaHumana
          .buscarPorUsuario(Long.valueOf(context.pathParam("id")));

      if (persona.isEmpty()) {
        throw new ValidacionFormularioException("No existe la persona.");
      }

      PersonaHumanaDTO dto = new PersonaHumanaDTO(persona.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("editado", false);
      model.put("id", context.pathParam("id"));
      context.render(rutaPersonaHbs, model);
    } catch (ValidacionFormularioException e) {
      model.put(ERROR, e.getMessage());
      context.render(rutaPersonaHbs, model);
    }
  }

  @Override
  public void update(Context context) {
    Map<String, Object> model = new HashMap<>();
    PersonaHumanaDTO dtoNuevo = new PersonaHumanaDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<PersonaHumana> personaExistente = repositorioPersonaHumana
          .buscarPorUsuario(Long.valueOf(context.pathParam("id")));

      if (personaExistente.isEmpty()) {
        throw new ValidacionFormularioException("Persona no encontrada.");
      }

      PersonaHumanaDTO dtoExistente = new PersonaHumanaDTO(personaExistente.get());
      if (dtoExistente.equals(dtoNuevo)) {
        throw new ValidacionFormularioException("No se detectaron cambios en el formulario.");
      }

      personaExistente.get().actualizarFromDto(dtoNuevo);
      withTransaction(() -> repositorioPersonaHumana.actualizar(personaExistente.get()));
      context.redirect(rutaPantallaPrincipal);
      PersonaHumana persona = personaExistente.get();
      persona.setNombre(dtoNuevo.getNombre());
      persona.setApellido(dtoNuevo.getApellido());
      persona.setFechaNacimiento(LocalDate.parse(dtoNuevo.getFechaNacimiento()));
      repositorioPersonaHumana.actualizar(persona);

      context.redirect(rutaPantallaPrincipal);
    } catch (ValidacionFormularioException e) {
      model.put(ERROR, e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("edicion", true);
      model.put("editado", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaPersonaHbs, model);
    }
  }

  @Override
  public void delete(Context context) {
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<PersonaHumana> persona = repositorioPersonaHumana.buscarPorUsuario(id);

    if (persona.isPresent()) {
      withTransaction(() -> {
        repositorioPersonaHumana.eliminarFisico(PersonaHumana.class, id);
        repositorioPersonaHumana.eliminarFisico(Usuario.class, id);
      });
      context.redirect(rutaPantallaPrincipal);
    } else {
      context.status(400).result("No se pudo eliminar la cuenta, reintente más tarde.");
    }
  }
}

