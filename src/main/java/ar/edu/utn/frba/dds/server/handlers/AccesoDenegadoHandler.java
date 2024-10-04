package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoException;
import io.javalin.Javalin;

public class AccesoDenegadoHandler implements IHandler {

  @Override
  public void setHandle(Javalin app) {
    app.exception(AccesoDenegadoException.class, (e, context) -> {
      context.status(401);
      context.render("401.hbs");
    });
  }
}