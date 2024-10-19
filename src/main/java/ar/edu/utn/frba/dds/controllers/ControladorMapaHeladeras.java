package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Desperfecto;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.FaltanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.QuedanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioSuscripcion;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorMapaHeladeras implements ICrudViewsHandler, WithSimplePersistenceUnit {

    private RepositorioHeladera repositorioHeladera;
    private RepositorioSuscripcion repositorioSuscripcion;
    private Repositorio repositorioPersonaJuridica;

    private final String rutaListadoHbs = "heladeras/mapaHeladerasPersonaHumana.hbs";
    private final String rutaListadoJuridicaHbs = "heladeras/mapaHeladerasPersonaJuridica.hbs";

    private final Gson gson = GsonFactory.createGson();

    public ControladorMapaHeladeras(RepositorioHeladera repositorioHeladera, Repositorio repositorioPersonaJuridica, RepositorioSuscripcion repositorioSuscripcion) {
        this.repositorioHeladera = repositorioHeladera;
        this.repositorioPersonaJuridica = repositorioPersonaJuridica;
        this.repositorioSuscripcion = repositorioSuscripcion;
    }

    // FIXME: Funciona unicamente en el caso de agregar heladeras y nada mas.
    // Si agregamos cambios de estado, solicitudes de apertura, sugerencias, etc., se rompe,
    // es decir que con el initializer completo rompe, pero solo agregando heladeras no.

    @Override
    public void index(Context context) {
        try {
            String terminoBusqueda = context.queryParam("q");  // El parámetro de búsqueda se espera en la query string
            List<Heladera> heladeras;

            if (terminoBusqueda != null && !terminoBusqueda.isEmpty()) {
                // Si hay un término de búsqueda, realizar la búsqueda
                heladeras = this.repositorioHeladera.buscarPorNombreODireccion(terminoBusqueda);
            } else {
                // Si no hay búsqueda, obtener todas las heladeras
                heladeras = this.repositorioHeladera.buscarTodos(Heladera.class);
            }

            String jsonHeladeras = gson.toJson(heladeras);
            Map<String, Object> model = new HashMap<>();
            model.put("heladeras", heladeras);
            model.put("jsonHeladeras", jsonHeladeras);
            model.put("buscadorMapa",true);

            context.render(rutaListadoHbs, model);
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result("Error interno del servidor");
        }
    }

    public void indexFiltro(Context context) {
        try {
            String terminoBusqueda = context.queryParam("q");  // El parámetro de búsqueda se espera en la query string
            List<Heladera> heladeras;

            if (terminoBusqueda != null && !terminoBusqueda.isEmpty()) {
                // Si hay un término de búsqueda, realizar la búsqueda
                heladeras = this.repositorioHeladera.buscarPorNombreODireccion(terminoBusqueda);
            } else {
                // Si no hay búsqueda, obtener todas las heladeras
                heladeras = this.repositorioHeladera.buscarTodos(Heladera.class);
            }

            String jsonHeladeras = gson.toJson(heladeras);
            Map<String, Object> model = new HashMap<>();
            model.put("heladeras", heladeras);
            model.put("jsonHeladeras", jsonHeladeras);
            model.put("buscadorMapa",true);

            context.render(rutaListadoJuridicaHbs, model);
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result("Error interno del servidor");
        }
    }

    @Override
    public void show(Context context) {
        try {
            Optional<Heladera> heladera = repositorioHeladera.buscarPorId(Long.parseLong(context.pathParam("heladeraId")), Heladera.class);
            if (heladera.isPresent()) {
                String jsonHeladera = gson.toJson(heladera.get());
                Map<String, Object> model = new HashMap<>();
                model.put("heladeraId",context.pathParam("heladeraId"));
                model.put("personaId",context.pathParam("personaId"));
                model.put("heladera", heladera.get());
                model.put("jsonHeladera", jsonHeladera);
                context.render("/heladeras/heladeraParticularPersonaHumana.hbs", model);
            } else {
                context.status(404).result("Heladera no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result("Error interno del servidor");
        }
    }
    public void showSelect(@NotNull Context context) {
        try {
            Optional<Heladera> heladera = repositorioHeladera.buscarPorId(Long.parseLong(context.pathParam("heladeraId")), Heladera.class);
            if (heladera.isPresent()) {
                String jsonHeladera = gson.toJson(heladera.get());

                Map<String, Object> model = new HashMap<>();
                model.put("heladeraId",context.pathParam("heladeraId"));
                model.put("personaId", 1);
                model.put("heladera", heladera.get());
                model.put("jsonHeladera", jsonHeladera);
                context.render("/heladeras/heladeraParticularPersonaJuridica.hbs", model);
            } else {
                context.status(404).result("Heladera no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500).result("Error interno del servidor");
        }
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
