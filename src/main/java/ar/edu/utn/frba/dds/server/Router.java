package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorDistribucionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionDinero;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladeras;
import ar.edu.utn.frba.dds.controllers.ControladorInicio;
import ar.edu.utn.frba.dds.controllers.ControladorInicioSesion;
import ar.edu.utn.frba.dds.controllers.ControladorMapaHeladeras;
import ar.edu.utn.frba.dds.controllers.ControladorOferta;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaHumana;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaJuridica;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import ar.edu.utn.frba.dds.controllers.ControladorReportes;
import ar.edu.utn.frba.dds.controllers.ControladorSuscripcion;
import ar.edu.utn.frba.dds.controllers.ControladorTecnicos;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import io.javalin.Javalin;

public class Router {

  public static void init(Javalin app) {
    // TODO: Indicar las rutas faltantes

    // Pantallas de inicio
    app.get("", ServiceLocator.instanceOf(ControladorInicio.class)::create);
    app.get("/sobreNosotros", context -> context.render("sobreNosotros.hbs"));

    // Registro
    app.get("/tipoCuenta", ServiceLocator.instanceOf(ControladorEleccionTipoCuenta.class)::create);
    app.post("/tipoCuenta", ServiceLocator.instanceOf(ControladorEleccionTipoCuenta.class)::save);
    app.get("/usuarios/nuevo", ServiceLocator.instanceOf(ControladorRegistroUsuario.class)::create);
    app.post("/usuarios", ServiceLocator.instanceOf(ControladorRegistroUsuario.class)::save);

    // Log in
    app.get("/inicioSesion", ServiceLocator.instanceOf(ControladorInicioSesion.class)::create);
    app.post("/inicioSesion", ServiceLocator.instanceOf(ControladorInicioSesion.class)::iniciarSesion);

    // Tecnico
    app.get("/tecnicos/nuevo", ServiceLocator.instanceOf(ControladorTecnicos.class)::create, TipoRol.ADMIN);
    app.post("/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::save, TipoRol.ADMIN);
    app.get("/tecnicos", ServiceLocator.instanceOf(ControladorTecnicos.class)::index, TipoRol.ADMIN);
    app.get("/tecnicos/{id}/edicion", ServiceLocator.instanceOf(ControladorTecnicos.class)::edit, TipoRol.ADMIN);
    app.post("/tecnicos/{id}/edicion", ServiceLocator.instanceOf(ControladorTecnicos.class)::update, TipoRol.ADMIN);
    app.post("/tecnicos/{id}/eliminacion", ServiceLocator.instanceOf(ControladorTecnicos.class)::delete, TipoRol.ADMIN);

    // Carga masiva
    app.get("/cargaMasiva/nuevo", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::create, TipoRol.ADMIN);
    app.post("/cargaMasiva", ServiceLocator.instanceOf(ControladorCargaMasiva.class)::save, TipoRol.ADMIN);

    // Reportes
    app.get("/reportes", ServiceLocator.instanceOf(ControladorReportes.class)::index, TipoRol.ADMIN);

    // Persona vulnerable
    app.get("/personasVulnerables/nuevo", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::create, TipoRol.PERSONA_HUMANA);
    app.post("/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::save, TipoRol.PERSONA_HUMANA);
    app.get("/personasVulnerables", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::index, TipoRol.PERSONA_HUMANA);
    app.get("/personasVulnerables/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::edit, TipoRol.PERSONA_HUMANA);
    app.post("/personasVulnerables/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::update, TipoRol.PERSONA_HUMANA);
    app.post("/personasVulnerables/{id}/eliminacion", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::delete, TipoRol.PERSONA_HUMANA);

    // Solicitud de tarjetas
    app.get("/personasVulnerables/solicitudTarjetas", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class):: solicitudTarjetas, TipoRol.PERSONA_HUMANA);
    app.post("/personasVulnerables/solicitudTarjetas", ServiceLocator.instanceOf(ControladorPersonaVulnerable.class)::solicitarTarjetas, TipoRol.PERSONA_HUMANA);

    // Personas humanas
    app.get("/personaHumana/nuevo", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::create, TipoRol.PERSONA_HUMANA);
    app.post("/personaHumana", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::save, TipoRol.PERSONA_HUMANA);
    app.get("/personaHumana/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::edit, TipoRol.PERSONA_HUMANA);
    app.post("/personaHumana/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::update, TipoRol.PERSONA_HUMANA);

    // Personas jurídicas
    app.get("/personaJuridica/nuevo", context -> context.render("cuenta/formularioPersonaJuridica.hbs"), TipoRol.PERSONA_JURIDICA);
    //app.get("/personaJuridica/nuevo", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::create, TipoRol.PERSONA_JURIDICA);
    app.post("/personaJuridica", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::save, TipoRol.PERSONA_JURIDICA);
    app.get("/personaJuridica/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::edit, TipoRol.PERSONA_JURIDICA);
    app.post("/personaJuridica/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::update, TipoRol.PERSONA_JURIDICA);

    // Donacion dinero
    app.get("/donacionDinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::index, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.get("/donacionDinero/nuevo", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::create, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/donacionDinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::save, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/donacionDinero/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::update, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.get("/donacionDinero/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::edit, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/donacionDinero/{id}/eliminacion", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::delete, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);

    // Donacion de vianda
    app.get("/donacionVianda", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::index, TipoRol.PERSONA_HUMANA);
    app.post("/donacionVianda", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::save, TipoRol.PERSONA_HUMANA);
    app.get("/donacionVianda/nuevo", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::create, TipoRol.PERSONA_HUMANA);
    app.get("/donacionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::edit, TipoRol.PERSONA_HUMANA);
    app.post("/donacionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::update, TipoRol.PERSONA_HUMANA);
    app.post("/donacionVianda/{id}/eliminacion", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::delete, TipoRol.PERSONA_HUMANA);

    // Distribución de viandas
    app.get("/distribucionVianda/nuevo", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::create, TipoRol.PERSONA_HUMANA);
    app.post("/distribucionVianda", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::save, TipoRol.PERSONA_HUMANA);
    app.get("/distribucionVianda", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::index, TipoRol.PERSONA_HUMANA);
    app.get("/distribucionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::edit, TipoRol.PERSONA_HUMANA);
    app.post("/distribucionVianda/{id}/edicion", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::update, TipoRol.PERSONA_HUMANA);
    app.post("/distribucionVianda/{id}/eliminacion", ServiceLocator.instanceOf(ControladorDistribucionVianda.class)::edit, TipoRol.PERSONA_HUMANA);

    // Ofertas. Persona humana: ver ofertas, canjear oferta
    //          Persona jurídica: ver sus ofertas, agregar oferta.
    app.get("/ofertas", ServiceLocator.instanceOf(ControladorOferta.class)::index, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.get("/ofertas/agregarOferta", ServiceLocator.instanceOf(ControladorOferta.class)::create, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.get("/ofertas/canjeadas", ServiceLocator.instanceOf(ControladorOferta.class)::verOfertasCanjeadas, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);

    app.post("/ofertas/agregarOferta", ServiceLocator.instanceOf(ControladorOferta.class)::save, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/ofertas/canjearOferta", ServiceLocator.instanceOf(ControladorOferta.class):: save, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("ofertas/{id}/eliminacion", ServiceLocator.instanceOf(ControladorOferta.class)::delete, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);

    // Mapa
    app.get("/mapaHeladeras", ServiceLocator.instanceOf(ControladorMapaHeladeras.class)::index, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);  // Lista de heladeras en el mapa
    app.get("/mapaHeladeras/{heladeraId}/HeladeraParticular", ServiceLocator.instanceOf(ControladorMapaHeladeras.class)::show, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);  // Detalle de una heladera
    app.get("/mapaHeladeras/PersonaJuridica", ServiceLocator.instanceOf(ControladorMapaHeladeras.class)::indexFiltro, TipoRol.PERSONA_JURIDICA);
    app.get("/mapaHeladeras/PersonaJuridica/{heladeraId}/HeladeraParticular", ServiceLocator.instanceOf(ControladorMapaHeladeras.class)::showSelect, TipoRol.PERSONA_JURIDICA);

    // Suscripcion
    app.get("/mapaHeladeras/{heladeraId}/suscripciones/persona/{personaId}/formulario", ServiceLocator.instanceOf(ControladorSuscripcion.class)::create, TipoRol.PERSONA_HUMANA);
    app.post("/mapaHeladeras/{heladeraId}/suscripciones/persona/{personaId}/suscribir", ServiceLocator.instanceOf(ControladorSuscripcion.class)::save, TipoRol.PERSONA_HUMANA);

    // Reporte de Falla
    app.get("/reportarFalla/{heladeraId}/reportar", ServiceLocator.instanceOf(ControladorIncidenteHeladeras.class)::create, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
    app.post("/reportarFalla/{heladeraId}/persona/{personaId}", ServiceLocator.instanceOf(ControladorIncidenteHeladeras.class)::save, TipoRol.PERSONA_HUMANA, TipoRol.PERSONA_JURIDICA);
  }
}