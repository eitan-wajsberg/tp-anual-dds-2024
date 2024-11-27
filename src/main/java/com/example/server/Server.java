package com.example.server;

import com.example.middleware.AuthMiddleware;
import com.example.utils.PrettyProperties;
import io.javalin.Javalin;

public class Server {
  private static Javalin app = null;

  public static Javalin app() {
    if (app == null) {
      throw new RuntimeException("App no inicializada");
    }
    return app;
  }

  public static void init() {
    if (app == null) {
      int port = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("server_port"));
      app = Javalin.create().start(port);
      AuthMiddleware.apply(app);
      Router.init(app);
    }
  }
}
