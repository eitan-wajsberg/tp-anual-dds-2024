package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorAltaTecnicos;
import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorInicio;
import ar.edu.utn.frba.dds.controllers.ControladorOferta;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.exceptions.AccesoDenegadoException;
import com.itextpdf.text.ListLabel;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    app.post("/tecnicos", ServiceLocator.instanceOf(ControladorAltaTecnicos.class)::save);
    app.get("/tecnicos", ServiceLocator.instanceOf(ControladorAltaTecnicos.class)::index);
    app.get("/admin/cargaMasiva", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::create);
    app.get("/admin/cargados", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::save);
    app.get("/admin/reportes", context -> context.render("admin/adminReportes.hbs"));

    // Persona vulnerable
    app.get("/colaboraciones/registroPersonaVulnerable", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::create);
    app.post("/colaboraciones/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::save);
    app.get("/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::index);

    //ofertas. Persona humana: ver ofertas, canjear oferta
    //         Persona jurídica: ver sus ofertas, agregar oferta.
    app.get("/colaboraciones/ofertas", ServiceLocator.instanceOf(ControladorOferta.class)::index);


    // Inicio
    app.get("/sobreNosotros", context -> context.render("sobreNosotros.hbs"));
    app.get("", ServiceLocator.instanceOf(ControladorInicio.class)::create);
  }
}