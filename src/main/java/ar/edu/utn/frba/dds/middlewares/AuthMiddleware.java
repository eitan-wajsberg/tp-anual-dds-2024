package ar.edu.utn.frba.dds.middlewares;

import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoException;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AuthMiddleware {

  public static void apply(Javalin app) {
    app.beforeMatched(ctx -> {
      var userRole = getUserRoleType(ctx);
      if (!ctx.routeRoles().isEmpty() && !ctx.routeRoles().contains(userRole)) {
        throw new AccesoDenegadoException("No estas autorizado para acceder a este contenido.", 401);
      }
    });
  }

  private static TipoRol getUserRoleType(Context context) {
    return context.sessionAttribute("rol") != null?
        TipoRol.valueOf(context.sessionAttribute("rol")) : null;
  }
}
