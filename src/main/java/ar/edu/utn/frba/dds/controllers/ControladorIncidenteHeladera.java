package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.dtos.IncidenteDTO;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorIncidenteHeladera implements WithSimplePersistenceUnit {
  private RepositorioHeladera repositorioHeladera;
  private Repositorio repositorioPersonaHumana;
  private Repositorio repositorioIncidente;
  private final String rutaReporteIncidente = "heladeras/reportarFalla.hbs";
  private final Gson gson = GsonFactory.createGson();

  public ControladorIncidenteHeladera(RepositorioHeladera repositorioHeladera, Repositorio repositorioPersonaHumana, Repositorio repositorioIncidente) {
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
    this.repositorioIncidente = repositorioIncidente;
  }

  public void create(Context context) {
    // Renderiza el formulario para reportar un incidente
    try {
      Optional<Heladera> heladera = repositorioHeladera.buscarPorId(Long.parseLong(context.pathParam("heladeraId")), Heladera.class);
      if (heladera.isPresent()) {
        Map<String, Object> model = new HashMap<>();
        model.put("heladeraId", heladera.get().getId());
        model.put("personaId", 1);
        context.render(rutaReporteIncidente, model);
      } else {
        context.status(404).result("Heladera no encontrada");
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.status(500).result("Error interno del servidor");
    }
  }

  public void save(Context ctx) {
    IncidenteDTO dto = new IncidenteDTO();
    dto.obtenerFormulario(ctx); // Obtenemos los datos desde el formulario
    try {
      // Buscar la heladera y el colaborador usando los IDs
      Optional<Heladera> heladera = repositorioHeladera.buscarPorId(dto.getHeladeraId(), Heladera.class);
      Optional<PersonaHumana> colaborador = repositorioPersonaHumana.buscarPorId(dto.getColaboradorId(), PersonaHumana.class);

      if (heladera.isPresent() && colaborador.isPresent()) {
        Incidente incidente = Incidente.fromDTO(dto,heladera.get(),colaborador.get());
        incidente.setFecha(LocalDateTime.now());
        // Guardar el incidente
        withTransaction( () -> repositorioIncidente.guardar(incidente));
        // Redirigir a la página de detalles de la heladera
        ctx.redirect("/mapaHeladeras/" + dto.getHeladeraId() + "/HeladeraParticular");
      } else {
        ctx.status(404).result("Heladera o colaborador no encontrado");
      }
    } catch (Exception e) {
      e.printStackTrace();
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Error al reportar el incidente.");
      model.put("dto", dto);
      ctx.render(rutaReporteIncidente, model);
    }
  }
}