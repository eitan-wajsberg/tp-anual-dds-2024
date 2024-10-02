package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;

public class ControladorEleccionTipoCuenta {

  public void create(Context context) {
    context.render("/cuenta/eleccionTipoDeCuenta.hbs");
  }

  public void save(Context context) {
    String tipoCuenta =   context.formParam("tipoCuenta");
    if (tipoCuenta != null && (tipoCuenta.equals("2") || tipoCuenta.equals("3"))) {
      context.sessionAttribute("tipoCuenta", tipoCuenta);
      context.redirect("/registro");
    } else {
      context.result("Tipo de cuenta inválido.");
    }
  }
}
