package ar.edu.utn.frba.dds.controllers;


import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.FaltanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.QuedanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Desperfecto;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioSuscripcion;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;


public class ControladorSuscripcion implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioSuscripcion repositorioSuscripcion;

    public ControladorSuscripcion(RepositorioSuscripcion repositorioSuscripcion) {
        this.repositorioSuscripcion = repositorioSuscripcion;
    }

    @Override
    public void index(Context context) {
    }

    @Override
    public void show(Context context) {
        // En este caso, no tiene sentido hacer este metodo.
    }

    @Override
    public void create(Context ctx) {
        // Obtener el DTO desde el contexto del formulario
        SuscripcionDTO dto = new SuscripcionDTO();
        dto.obtenerFormulario(ctx);

        // Obtener el tipo de suscripción desde el formulario
        String tipoSuscripcion = ctx.formParam("tipoSuscripcion");

        Suscripcion suscripcion;

        // Condicionar según el tipo de suscripción
        if ("FALTAN_N_VIANDAS".equals(tipoSuscripcion)) {
            suscripcion = FaltanNViandas.fromDTO(dto);
        } else if ("QUEDAN_N_VIANDAS".equals(tipoSuscripcion)) {
            suscripcion = QuedanNViandas.fromDTO(dto);
        } else if ("DESPERFECTO".equals(tipoSuscripcion)) {
            suscripcion = Desperfecto.fromDTO(dto);
        } else {
            throw new IllegalArgumentException("Tipo de suscripción no reconocido.");
        }

        repositorioSuscripcion.guardar(suscripcion);

        // Redireccionar o responder con éxito
        ctx.status(201).json(suscripcion);
    }
    @Override
    public void save(Context context) {

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
