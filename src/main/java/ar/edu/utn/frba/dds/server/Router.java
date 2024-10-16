package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorDistribucionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionDinero;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladeras;
import ar.edu.utn.frba.dds.controllers.ControladorInicio;
import ar.edu.utn.frba.dds.controllers.ControladorMapaHeladeras;
import ar.edu.utn.frba.dds.controllers.ControladorOferta;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaHumana;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaJuridica;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import ar.edu.utn.frba.dds.controllers.ControladorReportes;
import ar.edu.utn.frba.dds.controllers.ControladorSuscripcion;
import ar.edu.utn.frba.dds.controllers.ControladorTecnicos;
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
    app.get("/tipoCuenta", ServiceLocator.instanceOf(ControladorEleccionTipoCuenta.class)::create);
    app.post("/tipoCuenta", ServiceLocator.instanceOf(ControladorEleccionTipoCuenta.class)::save);
    app.get("/usuarios/nuevo", ServiceLocator.instanceOf(ControladorRegistroUsuario.class)::create);
    app.post("/usuarios", ServiceLocator.instanceOf(ControladorRegistroUsuario.class)::save);

    // Tecnico
    app.get("/tecnicos/nuevo", ServiceLocator.instanceOf(ControladorTecnicos.class)::create);
    app.post("/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::save);
    app.get("/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::index);
    app.get("/tecnicos/{id}/edicion", ServiceLocator.instanceOf(ControladorTecnicos.class)::edit);
    app.post("/tecnicos/{id}/edicion", ServiceLocator.instanceOf(ControladorTecnicos.class)::update);
    app.post("/tecnicos/{id}/eliminacion", ServiceLocator.instanceOf(ControladorTecnicos.class)::delete);

    // Carga masiva
    app.get("/cargaMasiva/nuevo", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::create);
    app.post("/cargaMasiva", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::save);

    // Reportes
    app.get("/reportes", ServiceLocator.instanceOf(ControladorReportes.class)::index);

    // Persona vulnerable
    app.get("/personasVulnerables/nuevo", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::create);
    app.post("/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::save);
    app.get("/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::index);
    app.get("/personasVulnerables/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::edit);
    app.post("/personasVulnerables/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::update);
    app.post("/personasVulnerables/{id}/eliminacion", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::delete);

    // PersonaHumana
    app.get("/personaHumana/nuevo", context -> context.render("cuenta/formularioPersonaHumana.hbs"));
    //app.get("/personaHumana/nuevo", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::create);
    app.post("/personaHumana", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::save); // Redirección a página principal
    app.get("/personaHumana/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::edit);
    app.post("/personaHumana/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::update); // Mostraría mensaje: "Cambios guardados correctamente"

    // PersonaJurídica
    app.get("/personaJuridica/nuevo", context -> context.render("cuenta/formularioPersonaJuridica.hbs"));
    //app.get("/personaJuridica/nuevo", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::create);
    app.post("/personaJuridica", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::save); // Redirección a página principal
    app.get("/personaJuridica/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::edit);
    app.post("/personaJuridica/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::update); //Mostraría mensaje: "Cambios guardados correctamente"

    // Donacion dinero 👌
    app.get("/donacionDinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::index);
    app.get("/donacionDinero/nuevo", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::create);
    app.post("/donacionDinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::save);
    app.post("/donacionDinero/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::update);
    app.get("/donacionDinero/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::edit);
    app.post("/donacionDinero/{id}/eliminacion", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::delete);

    // Donacion vianda
    //app.get("/donacionVianda", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::index);
    app.get("/donacionVianda", context -> context.render("colaboraciones/listadoDonacionesViandas.hbs"));
    app.post("/donacionVianda", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::save);
    //app.get("/donacionVianda/nuevo", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::create);
    app.get("/donacionVianda/nuevo", context -> context.render("colaboraciones/donacionVianda.hbs"));
    app.get("/donacionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::edit);
    app.post("/donacionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::update);
    app.post("/donacionVianda/{id}/eliminacion", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::delete);

    // Distribución viandas
    app.get("/distribucionVianda/nuevo", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::create);
    app.post("/distribucionVianda", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::save);
    app.get("/distribucionVianda", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::index);
    app.get("/distribucionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::edit);
    app.post("/distribucionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::update);
    app.post("/distribucionVianda/{id}/eliminacion", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::edit);

    //ofertas. Persona humana: ver ofertas, canjear oferta
    //         Persona jurídica: ver sus ofertas, agregar oferta.
    app.get("/ofertas", ServiceLocator.instanceOf(ControladorOferta.class)::index);
    app.get("/ofertas/agregarOferta", ServiceLocator.instanceOf(ControladorOferta.class)::create);
    app.get("/ofertas/canjeadas", ServiceLocator.instanceOf(ControladorOferta.class)::verOfertasCanjeadas);

    app.post("/ofertas/agregarOferta", ServiceLocator.instanceOf(ControladorOferta.class)::save);
    app.post("/ofertas/canjearOferta", ServiceLocator.instanceOf(ControladorOferta.class):: save);
    app.post("ofertas/{id}/eliminacion", ServiceLocator.instanceOf(ControladorOferta.class)::delete);

    // Inicio
    app.get("/sobreNosotros", context -> context.render("sobreNosotros.hbs"));
    app.get("", ServiceLocator.instanceOf(ControladorInicio.class)::create);

    // Mapa
    app.get("/mapaHeladeras", ServiceLocator.instanceOf(ControladorMapaHeladeras.class)::index);  // Lista de heladeras en el mapa
    app.get("/mapaHeladeras/{heladeraId}/HeladeraParticular", ServiceLocator.instanceOf(ControladorMapaHeladeras.class)::show);  // Detalle de una heladera
    // Suscripcion
    app.get("/mapaHeladeras/{heladeraId}/suscripciones/persona/{personaHumanaId}/nueva", ServiceLocator.instanceOf(ControladorSuscripcion.class)::create);
    app.post("/mapaHeladeras/{heladeraId}/suscripciones/persona/{personaHumanaId}", ServiceLocator.instanceOf(ControladorSuscripcion.class)::save);
    //Reporte de Falla
    app.get("/reportarFalla/{heladeraId}/reportar", ServiceLocator.instanceOf(ControladorIncidenteHeladeras.class)::create);
    app.post("/reportarFalla/{heladeraId}/persona/{personaId}", ServiceLocator.instanceOf(ControladorIncidenteHeladeras.class)::save);
  }
}