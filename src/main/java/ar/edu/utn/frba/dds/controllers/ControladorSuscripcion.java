package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Desperfecto;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.FaltanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.QuedanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioSuscripcion;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ControladorSuscripcion implements ICrudViewsHandler, WithSimplePersistenceUnit{

    private final String rutaSuscripcion = "heladeras/suscripcionAHeladera.hbs";

    private RepositorioHeladera repositorioHeladera;
    private RepositorioSuscripcion repositorioSuscripcion;

    public ControladorSuscripcion(RepositorioHeladera repositorioHeladera, RepositorioSuscripcion repositorioSuscripcion) {
        this.repositorioHeladera = repositorioHeladera;
        this.repositorioSuscripcion = repositorioSuscripcion;
    }


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    public void create(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("heladeraId", context.pathParam("heladeraId")); // Pasar el ID de la heladera al modelo
        model.put("personaId", 1); // Pasar el ID del usuario
        context.render(rutaSuscripcion, model);
    }

    public void save(Context ctx) {
        SuscripcionDTO dto = new SuscripcionDTO();
        dto.obtenerFormulario(ctx);

        String tipoSuscripcion = ctx.formParam("tipoSuscripcion");
        Suscripcion suscripcion;

        try {
            if ("FALTAN_N_VIANDAS".equals(tipoSuscripcion)) {
                suscripcion = FaltanNViandas.fromDTO(dto);
            } else if ("QUEDAN_N_VIANDAS".equals(tipoSuscripcion)) {
                suscripcion = QuedanNViandas.fromDTO(dto);
            } else if ("DESPERFECTO".equals(tipoSuscripcion)) {
                suscripcion = Desperfecto.fromDTO(dto);
            } else {
                throw new IllegalArgumentException("Tipo de suscripción no reconocido.");
            }

            // Guardar la suscripción
            withTransaction( () -> repositorioSuscripcion.guardar(suscripcion));

            // Redirigir de nuevo a la heladera particular
            ctx.redirect("/mapaHeladeras/" + dto.getIdHeladera() + "/HeladeraParticular");

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Error al procesar la suscripción.");
            model.put("dto", dto);
            ctx.render(rutaSuscripcion, model);
        }
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

}
