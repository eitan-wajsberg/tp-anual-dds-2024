package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionDinero;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorDonacionDinero implements ICrudViewsHandler {

  private final RepositorioDonacionDinero repositorioDonacionDinero;

  private final String rutaListadoHbs = "colaboraciones/listadoDonacionesDinero.hbs";
  private final String rutaRegistroHbs = "colaboraciones/donacionDinero.hbs";

  public ControladorDonacionDinero(RepositorioDonacionDinero repositorioDonacionDinero) {
    this.repositorioDonacionDinero = repositorioDonacionDinero;
  }

  @Override
  public void index(Context context){
    List<DonacionDinero> donacionesDeDinero = this.repositorioDonacionDinero.buscarTodos(DonacionDinero.class);

    Map<String, Object> model = new HashMap<>();
    model.put("donacionDinero", donacionesDeDinero);
    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    /*
    //RECIBE POR PATH PARAM EL ID DE UNA DONACION Y PRETENDE DEVOLVER UNA VISTA CON EL DETALLE DE ESA DONACION
    Optional<DonacionDinero> posibleProductoBuscado = this.repositorioDonacionDinero.buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

    //TODO
//        if(posibleProductoBuscado.isEmpty()) {
//            context.status(HttpStatus.NOT_FOUND);
//            return;
//        }

    Map<String, Object> model = new HashMap<>();
    model.put("producto", posibleProductoBuscado.get());

    context.render("productos/detalle_producto.hbs", model);
    */
  }

  public void create(Context context){
    context.render(rutaRegistroHbs);
  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {
    //...
  }

  @Override
  public void update(Context context) {
    //...
  }

  @Override
  public void delete(Context context) {
    //...
  }

}
