package ar.edu.utn.frba.dds.middlewares;

import io.javalin.Javalin;

public class AuthMiddleware {

  public static void apply(Javalin app) {
    app.beforeMatched(ctx -> {
      /* var userRole = getUserRoleType(ctx);
      if (!ctx.routeRoles().isEmpty() && !ctx.routeRoles().contains(userRole)) {
        throw new AccessDeniedException();
      }*/
    });
  }

  /* private static TipoRol getUserRoleType(Context context) {
    return context.sessionAttribute("tipo_rol") != null?
        TipoRol.valueOf(context.sessionAttribute("tipo_rol")) : null;
  }*/
}
