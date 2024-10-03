package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;

public class ControladorCargaMasiva {

  public void create(Context context) {
    context.render("admin/adminCargaCSV.hbs");
  }

  public void save(Context context) {
    // TODO
  }
}
