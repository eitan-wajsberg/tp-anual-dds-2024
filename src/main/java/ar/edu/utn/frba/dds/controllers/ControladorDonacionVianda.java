package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionVianda;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorDonacionVianda {

  private final RepositorioDonacionVianda repositorioDonacionVianda;

  private final String rutaListadoHbs = "colaboraciones/listadoDonacionesDinero.hbs";

  public ControladorDonacionVianda(RepositorioDonacionVianda repositorioDonacionVianda) {
    this.repositorioDonacionVianda = repositorioDonacionVianda;
  }

  public void index(Context context){
    List<Vianda> donacionesDeVianda = this.repositorioDonacionVianda.buscarTodos(Vianda.class);

    Map<String, Object> model = new HashMap<>();
    model.put("donacionesDeDinero", donacionesDeVianda);
    context.render(rutaListadoHbs, model);
  }

}
