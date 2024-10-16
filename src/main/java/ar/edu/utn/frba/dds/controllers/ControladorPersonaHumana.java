package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.adapters.AdaptadaJavaXMail;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.dtos.PersonaHumanaDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorPersonaHumana implements ICrudViewsHandler {

  private Repositorio repositorioGenerico;

  private final String rutaPersonaHbs = "cuenta/formularioPersonaHumana.hbs";

  public ControladorPersonaHumana(Repositorio repositorioGenerico) {
    this.repositorioGenerico = repositorioGenerico;
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

    try { //PersonaHumana nuevaPersona = PersonaHumana.fromDTO(dto);
      PersonaHumana nuevaPersona = new PersonaHumana();
      nuevaPersona.setNombre(dto.getNombre());
      nuevaPersona.setApellido(dto.getApellido());
      nuevaPersona.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
//      nuevaPersona.setDocumento(dto.getDocumento());
//      nuevaPersona.setContacto(dto.getContacto());
//      nuevaPersona.setDireccion(dto.getDireccion());

      repositorioGenerico.guardar(nuevaPersona);

      context.redirect(rutaPersonaHbs);
    } catch (Exception e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Error al procesar la persona. Intente nuevamente");
      model.put("dto", dto);
      context.render(rutaPersonaHbs, model);
    }
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<PersonaHumana> persona = repositorioGenerico.buscarPorId(Long.valueOf(context.pathParam("id")), PersonaHumana.class);

      if (persona.isEmpty()) {
        throw new RuntimeException("No existe la persona.");
      }

      PersonaHumanaDTO dto = new PersonaHumanaDTO(persona.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaPersonaHbs, model);
    } catch (Exception e) {
      model.put("error", e.getMessage());
      context.render(rutaPersonaHbs, model);
    }
  }

  @Override
  public void update(Context context) {
    Map<String, Object> model = new HashMap<>();
    PersonaHumanaDTO dtoNuevo = new PersonaHumanaDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<PersonaHumana> personaExistente = repositorioGenerico.buscarPorId(Long.valueOf(context.pathParam("id")), PersonaHumana.class);

      if (personaExistente.isEmpty()) {
        throw new RuntimeException("Persona no encontrada.");
      }

      PersonaHumana persona = personaExistente.get();
      persona.setNombre(dtoNuevo.getNombre());
      persona.setApellido(dtoNuevo.getApellido());
      persona.setFechaNacimiento(LocalDate.parse(dtoNuevo.getFechaNacimiento()));
//      persona.setDocumento(dtoNuevo.getDocumento());
//      persona.setContacto(dtoNuevo.getContacto());
//      persona.setDireccion(dtoNuevo.getDireccion());

      repositorioGenerico.actualizar(persona);

      context.redirect("/personaHumana");
    } catch (Exception e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaPersonaHbs, model);
    }
  }

  //TODO: Boton de eliminar cuenta?
  @Override
  public void delete(Context context) {
    /*
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<PersonaHumana> persona = repositorioGenerico.buscarPorId(id, PersonaHumana.class);

    if (persona.isPresent()) {
      repositorioGenerico.eliminarFisico(PersonaHumana.class, id);
      context.redirect("/personaHumana");
    } else {
      context.status(400).result("No se puede eliminar, la persona no fue encontrada.");
    }*/
  }

}

