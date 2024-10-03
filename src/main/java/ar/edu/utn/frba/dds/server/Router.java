package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorAltaTecnicos;
import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import io.javalin.Javalin;

public class Router {

  public static void init(Javalin app) {
    // TODO: Indicar las rutas faltantes

    // Registro
    app.get("/eleccion", ServiceLocator.instanceOf(ControladorEleccionTipoCuenta.class)::create);
    app.post("/eleccion", ServiceLocator.instanceOf(ControladorEleccionTipoCuenta.class)::save);
    app.get("/registro", ServiceLocator.instanceOf(ControladorRegistroUsuario.class)::create);
    app.post("/usuarios", ServiceLocator.instanceOf(ControladorRegistroUsuario.class)::save);

    // Admin
    app.get("/admin/altaTecnicos", ServiceLocator.instanceOf(ControladorAltaTecnicos.class)::create);
    app.get("/admin/tecnicos", ServiceLocator.instanceOf(ControladorAltaTecnicos.class)::save);
    app.get("/admin/cargaMasiva", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::create);
    app.get("/admin/cargados", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::save);
    app.get("/admin", context -> context.render("admin/adminInicio.hbs"));
    app.get("/admin/reportes", context -> context.render("admin/adminReportes.hbs"));

    // Persona vulnerable
    app.get("/colaboraciones/registroPersonaVulnerable", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::create);
    app.post("/colaboraciones/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::save);

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