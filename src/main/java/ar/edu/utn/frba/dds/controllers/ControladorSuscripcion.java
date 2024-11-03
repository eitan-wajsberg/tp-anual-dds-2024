package ar.edu.utn.frba.dds.controllers;


import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Desperfecto;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.FaltanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.QuedanNViandas;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioSuscripcion;
import ar.edu.utn.frba.dds.dtos.SuscripcionDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorSuscripcion implements WithSimplePersistenceUnit {
  private final String rutaSuscripcionHbs = "heladeras/heladeraParticular.hbs";
  private RepositorioHeladera repositorioHeladera;
  private RepositorioSuscripcion repositorioSuscripcion;
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private final Gson gson = GsonFactory.createGson();

  public ControladorSuscripcion(RepositorioHeladera repositorioHeladera, RepositorioSuscripcion repositorioSuscripcion, RepositorioPersonaHumana repositorioPersonaHumana) {
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioSuscripcion = repositorioSuscripcion;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }

  public void suscribir(Context context) {
    SuscripcionDTO dto = new SuscripcionDTO();
    dto.obtenerFormulario(context);

    Heladera heladera = null;
    // TODO: Buscar si ya tiene suscripciones
    //  si tiene actualizar en casoo de que corresponda
    //  si no tiene guardar la suscripcion.

    try {
      PersonaHumana personaHumana = obtenerPersonaHumana(context);
      heladera = obtenerHeladera(context);

      validarEntradas(dto);

      // Procesar suscripciones
      if (dto.getCantidadViandasFaltantes() != null) {
        crearYGuardarSuscripcion(personaHumana, heladera, true);
      }

      if (dto.getCantidadViandasQueQuedan() != null) {
        crearYGuardarSuscripcion(personaHumana, heladera, false);
      }

      if (dto.getDesperfecto()) {
        crearYGuardarDesperfecto(personaHumana, heladera);
      }

      // Redirigir a la heladera
      context.redirect("/heladeras/" + context.pathParam("heladeraId"));
    } catch (IllegalArgumentException e) {
      manejarError(context, e.getMessage(), dto, heladera);
    } catch (Exception e) {
      manejarError(context, "Ocurrió un error inesperado.", dto, null);
    }
  }

  private PersonaHumana obtenerPersonaHumana(Context context) {
    return this.repositorioPersonaHumana
        .buscarPorUsuario(context.sessionAttribute("id"))
        .orElseThrow(() -> new IllegalArgumentException("No se ha encontrado al usuario responsable."));
  }

  private Heladera obtenerHeladera(Context context) {
    return this.repositorioHeladera
        .buscarPorId(Long.valueOf(context.pathParam("heladeraId")))
        .orElseThrow(() -> new IllegalArgumentException("La heladera a la cual se quiere suscribir no fue encontrada."));
  }

  private void validarEntradas(SuscripcionDTO dto) {
    if (dto.getCantidadViandasFaltantes() == null && dto.getCantidadViandasQueQuedan() == null && !dto.getDesperfecto()) {
      throw new IllegalArgumentException("No se ha indicado ningún tipo de suscripción.");
    }

    if (dto.getCantidadViandasFaltantes() != null) {
      validarCantidad(dto.getCantidadViandasFaltantes());
    }

    if (dto.getCantidadViandasQueQuedan() != null) {
      validarCantidad(dto.getCantidadViandasQueQuedan());
    }
  }

  private void validarCantidad(Integer cantidad) {
    if (cantidad < 0) {
      throw new IllegalArgumentException("No se puede indicar una cantidad negativa.");
    }
  }

  private void crearYGuardarSuscripcion(PersonaHumana personaHumana, Heladera heladera, boolean esFaltanNViandas) {
    if (esFaltanNViandas) {
      FaltanNViandas faltanNViandas = new FaltanNViandas();
      faltanNViandas.setSuscriptor(personaHumana);
      faltanNViandas.setHeladera(heladera);
      withTransaction(() -> repositorioSuscripcion.guardar(faltanNViandas));
    } else {
      QuedanNViandas quedanNViandas = new QuedanNViandas();
      quedanNViandas.setSuscriptor(personaHumana);
      quedanNViandas.setHeladera(heladera);
      withTransaction(() -> repositorioSuscripcion.guardar(quedanNViandas));
    }
  }

  private void crearYGuardarDesperfecto(PersonaHumana personaHumana, Heladera heladera) {
    Desperfecto desperfecto = new Desperfecto();
    desperfecto.setSuscriptor(personaHumana);
    desperfecto.setHeladera(heladera);
    withTransaction(() -> repositorioSuscripcion.guardar(desperfecto));
  }

  private void manejarError(Context context, String errorMessage, SuscripcionDTO dto, Heladera heladera) {
    Map<String, Object> model = new HashMap<>();
    model.put("heladera", heladera);
    model.put("jsonHeladera", gson.toJson(heladera));
    model.put("mostrarPersonaHumana", true);
    if (heladera != null && !heladera.getViandas().isEmpty()) {
      model.put("hayViandas", true);
    }
    model.put("error", errorMessage);
    model.put("dto", dto);
    context.render(rutaSuscripcionHbs, model);
  }
}