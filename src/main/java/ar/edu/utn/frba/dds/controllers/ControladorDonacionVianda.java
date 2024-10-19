package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionVianda;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaJuridica;
import ar.edu.utn.frba.dds.dtos.ViandaDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorDonacionVianda implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private RepositorioDonacionVianda repositorioDonacionVianda;
  private final String rutaDonacionHbs = "colaboraciones/donacionVianda.hbs";
  private final String rutaListadoHbs = "colaboraciones/listadoDonacionesViandas.hbs";
  private final String rutaListadoDonaciones = "/donacionVianda";
  private final String ERROR = "error";


  public ControladorDonacionVianda(RepositorioPersonaHumana repositorioPersonaHumana, RepositorioDonacionVianda repositorioDonacionVianda) {
    this.repositorioPersonaHumana = repositorioPersonaHumana;
    this.repositorioDonacionVianda = repositorioDonacionVianda;
  }

  @Override
  public void index(Context context) {
    Long id = context.sessionAttribute("id");
    List<Vianda> donaciones = this.repositorioDonacionVianda.buscarViandasDe(id);
    Map<String, Object> model = new HashMap<>();
    model.put("donacionesVianda", donaciones);
    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    // No specific show method needed.
  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();
    model.put("title", "Donar vianda");
    context.render(rutaDonacionHbs, model);
  }

  @Override
  public void save(Context context) {
    ViandaDTO dto = new ViandaDTO();
    dto.obtenerFormulario(context);
    Long id = context.sessionAttribute("id");

    try {
      Optional<PersonaHumana> personaHumana = repositorioPersonaHumana.buscarPorUsuario(id);
      if (personaHumana.isEmpty()) {
          throw new ValidacionFormularioException("No se ha encontrado el id del usuario. Error en servidor.");
      }

      Vianda nuevaDonacion = Vianda.fromDTO(dto);
      if (nuevaDonacion == null) {
        throw new ValidacionFormularioException("Se ha ingresado información incorrecta sobre la donación.");
      }

      personaHumana.get().sumarPuntaje(nuevaDonacion.calcularPuntaje());
      withTransaction(() -> {
        repositorioDonacionVianda.guardar(nuevaDonacion);
        repositorioPersonaHumana.actualizar(personaHumana);
      });
      context.redirect(rutaListadoDonaciones);
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put(ERROR, e.getMessage());
      model.put("dto", dto);
      context.render(rutaDonacionHbs, model);
    }
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<Vianda> vianda = repositorioDonacionVianda
          .buscarPorId(Long.valueOf(context.pathParam("id")), Vianda.class);

      if (vianda.isEmpty()) {
        throw new ValidacionFormularioException("No existe la donación de vianda.");
      }

      ViandaDTO dto = new ViandaDTO(vianda.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      model.put("title", "Editar donacion");
      context.render(rutaDonacionHbs, model);
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      context.render(rutaListadoHbs, model);
    }
  }

  @Override
  public void update(Context context) {
    Map<String, Object> model = new HashMap<>();
    ViandaDTO dtoNuevo = new ViandaDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<Vianda> viandaExistente = repositorioDonacionVianda
          .buscarPorId(Long.valueOf(context.pathParam("id")), Vianda.class);

      if (viandaExistente.isEmpty()) {
        throw new ValidacionFormularioException("Donación de vianda no encontrada.");
      }

      ViandaDTO dtoExistente = new ViandaDTO(viandaExistente.get());
      if (dtoExistente.equals(dtoNuevo)) {
        throw new ValidacionFormularioException("No se detectaron cambios en el formulario.");
      }

      viandaExistente.get().actualizarFromDto(dtoNuevo);
      withTransaction(() -> repositorioDonacionVianda.actualizar(viandaExistente.get()));
      context.redirect(rutaListadoDonaciones);

    } catch (Exception e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaDonacionHbs, model);
    }
  }

  @Override
  public void delete(Context context) {
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<Vianda> vianda = repositorioDonacionVianda.buscarPorId(id, Vianda.class);

    if (vianda.isPresent()) {
      withTransaction(() -> this.repositorioDonacionVianda.eliminarFisico(Vianda.class, id));
      context.redirect(rutaListadoDonaciones);
    } else {
      context.status(400).result("No se puede cancelar la donación de vianda, pues no fue encontrada.");
    }
  }
}
