package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.javalin.http.Context;

public class ControladorCargaMasiva {

  private final String rutaHbs = "/admin/adminCargaMasiva.hbs";

  public void create(Context context) {
    context.render(rutaHbs);
  }

  public void save(Context context) {
    // TODO
  }
}
