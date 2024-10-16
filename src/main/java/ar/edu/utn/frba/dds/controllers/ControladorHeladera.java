package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoCuentaRegistro;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorHeladera implements ICrudViewsHandler {
  private RepositorioHeladera repositoriaHeladera;
  private final String rutaListadoHbs = "heladeras.hbs";

  public ControladorHeladera(RepositorioHeladera repositoriaHeladera) {
    this.repositoriaHeladera = repositoriaHeladera;
  }

  @Override
  public void index(Context context) {
    List<Heladera> heladeras = this.repositoriaHeladera.buscarTodos(Heladera.class);

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladeras);

    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }

  public static HeladeraDTO fromEntity(Heladera entity){
    return HeladeraDTO.builder().build();
  }
}
