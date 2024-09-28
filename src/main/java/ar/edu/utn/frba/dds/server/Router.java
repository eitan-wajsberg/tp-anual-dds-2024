package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import io.javalin.Javalin;

public class Router {

  public static void init(Javalin app) {
    app.get("/prueba", ctx -> ctx.result("Hola mundo!"));
    // TODO: Indicar las rutas
  }
}