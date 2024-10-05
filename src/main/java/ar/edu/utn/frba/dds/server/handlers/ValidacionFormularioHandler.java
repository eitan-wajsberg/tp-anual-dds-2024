package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class ValidacionFormularioHandler implements IHandler {

  @Override
  public void setHandle(Javalin app) {
    app.exception(ValidacionFormularioException.class, (e, context) -> {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      context.render(e.getRutaHandlebar(), model);
    });
  }
}