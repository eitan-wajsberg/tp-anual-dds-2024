package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class Router {

  public static void init(Javalin app) {
    // TODO: Indicar las rutas

    // Registro e inicio de sesion
    app.get("/eleccion", context -> context.render("/cuenta/eleccionTipoDeCuenta.hbs"));
    app.get("/registro", context -> context.render("/cuenta/crearCuenta.hbs"));

    // Admin
    app.get("/admin/altaTecnicos", context -> context.render("admin/adminAltaTecnicos.hbs"));
    app.get("/admin/cargaCSV", context -> context.render("admin/adminCargaCSV.hbs"));
    app.get("/admin", context -> context.render("admin/adminInicio.hbs"));
    app.get("/admin/reportes", context -> context.render("admin/adminReportes.hbs"));

    // Contribuciones
    app.get("/colaboraciones/registroPersonaVulnerable", context -> context.render("colaboraciones/registroPersonaVulnerable.hbs"));

    // Inicio
    app.get("", context -> context.render("pantallaInicio.hbs"));
    app.get("/sobreNosotros", context -> context.render("sobreNosotros.hbs"));
    // TODO: (PROVISIONAL) - Falta ver el concepto de session
    if (true) {
      app.get("/inicio", context -> context.render("pantallaInicioHumana.hbs"));
    } else {
      app.get("/inicio", context -> context.render("pantallaInicioJuridica.hbs"));
    }
  }
}