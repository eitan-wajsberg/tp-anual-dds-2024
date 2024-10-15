package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.adapters.LocalDateTimeAdapter;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
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
       /* PersonaVulnerableDTO dto = new PersonaVulnerableDTO();
        dto.obtenerFormulario(context);

        try {
            Optional<PersonaHumana> registrador = repositorioPersonaHumana.buscarPorId(1L, PersonaHumana.class);
            if (registrador.isEmpty()) {
                throw new ValidacionFormularioException("No se ha encontrado la persona que lo está registrando. Reintentar.");
            }

            PersonaVulnerable nuevaPersona = PersonaVulnerable.fromDTO(dto);
            if (nuevaPersona == null) {
                throw new ValidacionFormularioException("Se ha ingresado información incorrecta sobre la persona vulnerable.");
            }

            nuevaPersona.setPersonaQueLoRegistro(registrador.get());
            nuevaPersona.setFechaDeRegistro(LocalDate.now());

            withTransaction(() -> repositorioPersonaVulnerable.guardar(nuevaPersona));

            context.redirect("/personasVulnerables");
        } catch (ValidacionFormularioException e) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", e.getMessage());
            model.put("dto", dto);
            context.render(rutaRegistroHbs, model);
        }
        */
    }

    @Override
    public void edit(Context context) {
       /* Map<String, Object> model = new HashMap<>();
        try {
            Optional<PersonaVulnerable> vulnerable = this.repositorioPersonaVulnerable.buscarPorId(Long.valueOf(context.pathParam("id")), PersonaVulnerable.class);

            if (vulnerable.isEmpty()) {
                throw new ValidacionFormularioException("No existe una persona vulnerable con este id.");
            }

            PersonaVulnerableDTO dto = new PersonaVulnerableDTO(vulnerable.get());
            model.put("dto", dto);
            model.put("edicion", true);
            model.put("id", context.pathParam("id"));
            context.render(rutaRegistroHbs, model);
        } catch (ValidacionFormularioException e) {
            model.put("error", e.getMessage());
            context.render(rutaListadoHbs, model);
        }*/
    }

    @Override
    public void update(Context context) {
       /* Map<String, Object> model = new HashMap<>();
        PersonaVulnerableDTO dtoNuevo = new PersonaVulnerableDTO();
        dtoNuevo.obtenerFormulario(context);

        try {
            Optional<PersonaVulnerable> vulnerableExistente = repositorioPersonaVulnerable.buscarPorId(
                    Long.valueOf(context.pathParam("id")), PersonaVulnerable.class);

            if (vulnerableExistente.isEmpty()) {
                throw new ValidacionFormularioException("Persona vulnerable no encontrada.");
            }

            PersonaVulnerableDTO dtoExistente = new PersonaVulnerableDTO(vulnerableExistente.get());
            if (dtoExistente.equals(dtoNuevo)) {
                throw new ValidacionFormularioException("No se detectaron cambios en el formulario.");
            }

            vulnerableExistente.get().actualizarFromDto(dtoNuevo);
            withTransaction(() -> repositorioPersonaVulnerable.actualizar(vulnerableExistente.get()));
            context.redirect("/personasVulnerables");
        } catch (ValidacionFormularioException e) {
            model.put("error", e.getMessage());
            model.put("dto", dtoNuevo);
            model.put("edicion", true);
            model.put("id", context.pathParam("id"));
            context.render(rutaRegistroHbs, model);
        }*/
    }

    @Override
    public void delete(Context context) {
       /* Long id = Long.valueOf(context.pathParam("id"));
        Optional<PersonaVulnerable> persona = this.repositorioPersonaVulnerable.buscarPorId(id, PersonaVulnerable.class);
        if (persona.isPresent()) {
            withTransaction(() -> this.repositorioPersonaVulnerable.eliminarFisico(PersonaVulnerable.class, id));
            context.redirect("/personasVulnerables");
        } else {
            context.status(400).result("No se puede eliminar, la persona no cumple con las condiciones para ser eliminada.");
        }*/
    }

}
