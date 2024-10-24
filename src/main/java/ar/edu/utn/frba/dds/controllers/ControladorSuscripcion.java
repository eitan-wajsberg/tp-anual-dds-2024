package ar.edu.utn.frba.dds.controllers;


import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Desperfecto;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.FaltanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.QuedanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioSuscripcion;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class ControladorSuscripcion implements WithSimplePersistenceUnit {
  private final String rutaSuscripcionHbs = "heladeras/heladeraParticular.hbs";
  private RepositorioHeladera repositorioHeladera;
  private RepositorioSuscripcion repositorioSuscripcion;

  public ControladorSuscripcion(RepositorioHeladera repositorioHeladera, RepositorioSuscripcion repositorioSuscripcion) {
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioSuscripcion = repositorioSuscripcion;
  }

  public void save(Context context) {
    SuscripcionDTO dto = new SuscripcionDTO();
    dto.obtenerFormulario(context);

    String tipoSuscripcion = context.formParam("tipoSuscripcion");
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
      context.redirect("/mapaHeladeras/" + dto.getIdHeladera() + "/HeladeraParticular");

    } catch (Exception e) {
      e.printStackTrace();
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Error al procesar la suscripción.");
      model.put("dto", dto);
      context.render(rutaSuscripcionHbs, model);
    }
  }
}