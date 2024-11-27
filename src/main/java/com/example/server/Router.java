package com.example.server;

import com.example.controllers.OAuthController;
import io.javalin.Javalin;

public class Router {

  public static void init(Javalin app) {
    // Public endpoints
    app.get("/", ctx -> ctx.result("Bienvenido a la PoC de OAuth!"));
    app.get("/login", OAuthController::redirect);
    app.get("/oauth/callback", OAuthController::callback);

    // Protected endpoints
    app.get("/protected", ctx -> ctx.result("Acceso autorizado a contenido protegido."));
  }
}
