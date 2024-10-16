package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaVulnerable;
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
import java.util.TimeZone;

public class ControladorPersonaVulnerable implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioPersonaVulnerable repositorioPersonaVulnerable;
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private final String rutaRegistroHbs = "/colaboraciones/registroPersonaVulnerable.hbs";
  private final String rutaListadoHbs = "colaboraciones/listadoPersonasVulnerables.hbs";

  public ControladorPersonaVulnerable(RepositorioPersonaVulnerable repositorioPersonaVulnerable, RepositorioPersonaHumana repositorioPersonaHumana) {
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }

  @Override
  public void index(Context context) {
    // FIXME: Quitar la persona hardcodeada y obtener el id de la sesion
    Optional<List<PersonaVulnerable>> vulnerables = this.repositorioPersonaVulnerable.buscarPersonasDe(1L);

    Map<String, Object> model = new HashMap<>();
    vulnerables.ifPresent(personaVulnerables -> model.put("personasVulnerables", personaVulnerables));

    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    // En este caso, no tiene sentido hacer este metodo.
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
      // FIXME: Quitar la persona hardcodeada y obtener el id de la sesion
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

      // asigno tarjeta sin entregar del registrador a la persona vulnerable
      Tarjeta tarjeta = asignarTarjeta(registrador.get());
      registrador.get().agregarTarjetaEntregada(tarjeta);
      nuevaPersona.setTarjetaEnUso(tarjeta);

      registrador.get().sumarPuntaje(tarjeta.calcularPuntaje());
      withTransaction(() -> {
        repositorioPersonaVulnerable.guardar(nuevaPersona);
        repositorioPersonaHumana.actualizar(registrador);
      });

      context.redirect("/personasVulnerables");
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      context.render(rutaRegistroHbs, model);
    }
  }

  private Tarjeta asignarTarjeta(PersonaHumana registrador) throws ValidacionFormularioException {
    if (registrador.getTarjetasSinEntregar().isEmpty()) {
      throw new ValidacionFormularioException("No puede registrar personas vulnerables ya que no tiene tarjetas para entregar.");
    }
    Tarjeta tarjeta = registrador.getTarjetasSinEntregar().remove(0);
    tarjeta.setFechaRecepcionPersonaVulnerable(LocalDate.now());
    return tarjeta;
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<PersonaVulnerable> vulnerable = this.repositorioPersonaVulnerable.buscarPorId(Long.valueOf(context.pathParam("id")), PersonaVulnerable.class);

      if (vulnerable.isEmpty()) {
        throw new ValidacionFormularioException("No existe una persona vulnerable con este id.");
      }

      PersonaVulnerableDTO dto = new PersonaVulnerableDTO(vulnerable.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaRegistroHbs, model);
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      context.render(rutaListadoHbs, model);
    }
  }

  @Override
  public void update(Context context) {

    Map<String, Object> model = new HashMap<>();
    PersonaVulnerableDTO dtoNuevo = new PersonaVulnerableDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<PersonaVulnerable> vulnerableExistente = repositorioPersonaVulnerable
          .buscarPorId(Long.valueOf(context.pathParam("id")), PersonaVulnerable.class);

      if (vulnerableExistente.isEmpty()) {
        throw new ValidacionFormularioException("Persona vulnerable no encontrada.");
      }

      PersonaVulnerableDTO dtoExistente = new PersonaVulnerableDTO(vulnerableExistente.get());
      if (dtoExistente.equals(dtoNuevo)) {
        throw new ValidacionFormularioException("No se detectaron cambios en el formulario.");
      }

      vulnerableExistente.get().actualizarFromDto(dtoNuevo);
      withTransaction(() -> repositorioPersonaVulnerable.actualizar(vulnerableExistente.get()));
      context.redirect("/personasVulnerables");
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaRegistroHbs, model);
    }
  }

  @Override
  public void delete(Context context) {
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<PersonaVulnerable> persona = this.repositorioPersonaVulnerable.buscarPorId(id, PersonaVulnerable.class);
    if (persona.isPresent()) {
      withTransaction(() -> this.repositorioPersonaVulnerable.eliminarFisico(PersonaVulnerable.class, id));
      context.redirect("/personasVulnerables");
    } else {
      context.status(400).result("No se puede eliminar, la persona no cumple con las condiciones para ser eliminada.");
    }
  }



}