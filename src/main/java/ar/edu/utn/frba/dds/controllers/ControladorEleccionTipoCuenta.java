package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.javalin.http.Context;

public class ControladorEleccionTipoCuenta {
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("ruta_eleccion_tipo_cuenta");

  public void create(Context context) {
    context.render(rutaHbs);
  }

  public void save(Context context) {
    // FIXME: Arreglar el manejo de roles
    String tipoCuenta =   context.formParam("tipoCuenta");
    if (tipoCuenta != null && (tipoCuenta.equals("2") || tipoCuenta.equals("3"))) {
      context.sessionAttribute("tipoCuenta", tipoCuenta);
      context.redirect("/registro");
    } else {
      context.result("Tipo de cuenta inválido.");
    }
  }
}
