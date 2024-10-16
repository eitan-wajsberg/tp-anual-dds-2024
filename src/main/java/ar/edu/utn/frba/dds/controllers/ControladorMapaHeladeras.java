package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorMapaHeladeras implements ICrudViewsHandler, WithSimplePersistenceUnit {
    private RepositorioHeladera repositorioHeladera;
    private Repositorio repositorioPersonaJuridica;
    private final String rutaListadoHbs = "heladeras/mapaHeladerasPersonaHumana.hbs";

    // Configurar Gson con un adaptador para LocalDateTime
    private final Gson gson = GsonFactory.createGson();

    public ControladorMapaHeladeras(RepositorioHeladera repositorioHeladera, Repositorio repositorioPersonaJuridica) {
        this.repositorioHeladera = repositorioHeladera;
        this.repositorioPersonaJuridica = repositorioPersonaJuridica;
    }

    @Override
    public void index(Context context) {
        try {
            List<Heladera> heladeras = this.repositorioHeladera.buscarTodos(Heladera.class);
            String jsonHeladeras = gson.toJson(heladeras);
            Map<String, Object> model = new HashMap<>();
            model.put("heladeras", heladeras);
            model.put("jsonHeladeras", jsonHeladeras);
            context.render(rutaListadoHbs, model);
        } catch (Exception e) {
            e.printStackTrace();  // Esto imprimirá el error completo y te ayudará a identificar la causa exacta
            context.status(500).result("Error interno del servidor");
        }

    }

    public void mostrarHeladeraParticular(Context context, String id) {
        try {
            // Buscar la heladera por su ID
            Optional<Heladera> heladera = repositorioHeladera.buscarPorId(Long.parseLong(id), Heladera.class);
            if (heladera.isPresent()) {
                String jsonHeladera = gson.toJson(heladera.get());  // Convierte solo el objeto dentro del Optional
                Map<String, Object> model = new HashMap<>();
                model.put("heladera", heladera.get());
                model.put("jsonHeladera", jsonHeladera);
                context.render("heladeras/heladeraParticularPersonaHumana.hbs", model);
            } else {
                context.status(404).result("Heladera no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result("Error interno del servidor");
        }
    }


@Override
    public void show(Context context) {
        // En este caso, no tiene sentido hacer este metodo.
    }

    @Override
    public void create(Context context) {

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
