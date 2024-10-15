package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorTecnicos implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioTecnicos repositorioTecnicos;
  private final String rutaAltaHbs = "/admin/adminAltaTecnicos.hbs";
  private final String rutaListadoHbs = "admin/adminListadoTecnicos.hbs";

  public ControladorTecnicos(RepositorioTecnicos repositorioTecnico) {
    this.repositorioTecnicos = repositorioTecnico;
  }

  @Override
  public void index(Context context) {
    List<Tecnico> tecnicos = this.repositorioTecnicos.buscarTodos(Tecnico.class);

    Map<String, Object> model = new HashMap<>();
    model.put("tecnicos", tecnicos);

    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    // En este caso, no tiene sentido hacer este metodo.
  }

  @Override
  public void create(Context context) {
    context.render(rutaAltaHbs);
  }

  @Override
  public void save(Context context) {
    TecnicoDTO dto = new TecnicoDTO();
    dto.obtenerFormulario(context);
    Tecnico nuevoTecnico;

    try {
      nuevoTecnico = Tecnico.fromDTO(dto);
      if (nuevoTecnico == null) {
        throw new ValidacionFormularioException("Los datos del técnico son inválidos.");
      }

      withTransaction(() -> repositorioTecnicos.guardar(nuevoTecnico));

      context.redirect("/tecnicos");
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      context.render(rutaAltaHbs, model);
    }
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<Tecnico> tecnico = this.repositorioTecnicos.buscarPorId(Long.valueOf(context.pathParam("id")), Tecnico.class);

      if (tecnico.isEmpty()) {
        throw new ValidacionFormularioException("No existe un técnico con este id.");
      }

      TecnicoDTO dto = new TecnicoDTO(tecnico.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaAltaHbs, model);
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      context.render(rutaListadoHbs, model);
    }
  }

  @Override
  public void update(Context context) {
    Map<String, Object> model = new HashMap<>();
    TecnicoDTO dtoNuevo = new TecnicoDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<Tecnico> tecnicoExistente = repositorioTecnicos.buscarPorId(
          Long.valueOf(context.pathParam("id")), Tecnico.class);

      if (tecnicoExistente.isEmpty()) {
        throw new ValidacionFormularioException("Tecnico no encontrada.");
      }

      TecnicoDTO dtoExistente = new TecnicoDTO(tecnicoExistente.get());
      if (dtoExistente.equals(dtoNuevo)) {
        throw new ValidacionFormularioException("No se detectaron cambios en el formulario.");
      }

      tecnicoExistente.get().actualizarFromDto(dtoNuevo);
      withTransaction(() -> repositorioTecnicos.actualizar(tecnicoExistente.get()));
      context.redirect("/tecnicos");
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaAltaHbs, model);
    }
  }

  @Override
  public void delete(Context context) {
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<Tecnico> persona = this.repositorioTecnicos.buscarPorId(id, Tecnico.class);
    if (persona.isPresent()) {
      withTransaction(() -> this.repositorioTecnicos.eliminarFisico(Tecnico.class, id));
      context.redirect("/tecnicos");
    } else {
      context.status(400).result("No se puede eliminar, el técnico no cumple con las condiciones para ser eliminada.");
    }
  }
}
