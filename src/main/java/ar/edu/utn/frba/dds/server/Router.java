package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionDinero;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaHumana;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaJuridica;
import ar.edu.utn.frba.dds.controllers.ControladorTecnicos;
import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorInicio;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import ar.edu.utn.frba.dds.controllers.ControladorReportes;
import io.javalin.Javalin;

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

    // Formulario de registro: personaHumana
    //app.get("/personaHumana/nuevo", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::create);
    //app.post("/personaHumana", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::save);
    // Tras post, mostraría mensaje: "Registro realizado correctamente" + redirección a página principal

    // Edición de datos personales: personaHumana
    //TODO: app.get("/personaHumana/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::edit);
    //TODO: app.post("/personaHumana/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaHumana.class)::update);
    //Mostraría mensaje: "Cambios guardados correctamente"

    // Formulario de registro: personaJurídica
    //app.get("/personaJuridica/nuevo", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::create);
    //app.post("/personaJuridica", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::save);
    // Tras post, mostraría mensaje: "Registro realizado correctamente" + redirección a página principal

    // Edición de datos personales: personaJuridica
    // TODO: app.get("/personaJuridica/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::edit);
    // TODO: app.post("/personaJuridica/{id}/edicion", ServiceLocator.instanceOf(ControladorPersonaJuridica.class)::update);
    //Mostraría mensaje: "Cambios guardados correctamente"

    //Eliminar cuenta?


    // Donacion dinero
    app.get("/donacionDinero/nuevo", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::create);
    app.post("/donacionDinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::save);
    app.get("/donacionDinero", ServiceLocator.instanceOf(ControladorDonacionDinero.class)::index);
    //Dar de baja/modificar donaciones de dinero.

    // Donacion vianda
    app.get("/donacionVianda/nuevo", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::create);
    app.post("/donacionVianda", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::save);
    app.get("/donacionVianda", ServiceLocator.instanceOf(ControladorDonacionVianda.class)::index);
    //Cancelar/Modificar donación de vianda.



    // Inicio
    app.get("/sobreNosotros", context -> context.render("sobreNosotros.hbs"));
    app.get("", ServiceLocator.instanceOf(ControladorInicio.class)::create);
  }
}