package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioOferta;
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

public class ControladorOferta implements WithSimplePersistenceUnit, ICrudViewsHandler {
  private RepositorioOferta repositorioOferta;
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("hbs_ofertas");

  public ControladorOferta(RepositorioOferta repositorioOferta) {
    this.repositorioOferta = repositorioOferta;
  }

  @Override
  public void index(Context context) {
//    List<Oferta> ofertas = this.repositorioOferta.buscarTodos(Oferta.class);
//
//    Map<String, Object> model = new HashMap<>();
//    model.put("ofertas", ofertas);
//    model.put("titulo", "Listado de ofertas");

    context.render(rutaHbs);
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
