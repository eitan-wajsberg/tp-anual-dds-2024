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
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("hbs_alta_tecnicos");

  public ControladorAltaTecnicos(RepositorioTecnicos repositorioTecnico) {
    this.repositorioTecnicos = repositorioTecnico;
  }

  @Override
  public void index(Context context) {
    List<Tecnico> tecnicos = this.repositorioTecnicos.buscarTodos(Tecnico.class);

    Map<String, Object> model = new HashMap<>();
    model.put("tecnicos", tecnicos);

    context.render("admin/adminListadoTecnicos.hbs", model);
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
    TecnicoDTO dto = new TecnicoDTO();
    dto.obtenerFormulario(context, rutaHbs);
    Tecnico nuevoTecnico = Tecnico.fromDTO(dto);

    if (nuevoTecnico == null) {
      throw new ValidacionFormularioException("Los datos del técnico son inválidos.", rutaHbs);
    }
    
    withTransaction(() -> repositorioTecnicos.guardar(nuevoTecnico));

    context.result("<script>localStorage.clear();</script>");
    context.redirect("/tecnicos");
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
