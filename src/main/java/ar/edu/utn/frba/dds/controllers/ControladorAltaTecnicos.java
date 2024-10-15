package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorAltaTecnicos implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioTecnicos repositorioTecnicos;
  private final String rutaAltaHbs = "/admin/adminAltaTecnicos.hbs";
  private final String rutaListadoHbs = "admin/adminListadoTecnicos.hbs";

  public ControladorAltaTecnicos(RepositorioTecnicos repositorioTecnico) {
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
    // TODO
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

      context.redirect(rutaListadoHbs);
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      context.render(rutaAltaHbs, model);
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
