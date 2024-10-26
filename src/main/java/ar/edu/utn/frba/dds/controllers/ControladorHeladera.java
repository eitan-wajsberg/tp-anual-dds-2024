package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Modelo;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Municipio;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef.GeoRefServicio;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioGeoRef;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaJuridica;
import ar.edu.utn.frba.dds.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorHeladera implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioHeladera repositorioHeladera;
  private RepositorioPersonaJuridica repositorioPersonaJuridica;
  private RepositorioGeoRef repositorioGeoRef;
  private final String rutaAltaHbs = "colaboraciones/cuidarHeladera.hbs";
  private final String rutaRecomendacionHbs = "colaboraciones/recomendacionDePuntos.hbs";
  private final String rutaParticularHbs = "heladeras/heladeraParticular.hbs";
  private final Gson gson = GsonFactory.createGson();

  public ControladorHeladera(RepositorioHeladera repositoriaHeladera, RepositorioGeoRef repositorioGeoRef, RepositorioPersonaJuridica repositorioPersonaJuridica) {
    this.repositorioHeladera = repositoriaHeladera;
    this.repositorioGeoRef = repositorioGeoRef;
    this.repositorioPersonaJuridica = repositorioPersonaJuridica;
  }

  @Override
  public void index(Context context) {
    // tiene sentido? si ya estan plasmadas en el mapa
  }

  public void show(Context context) {
    try {
      // Obtener el ID de la heladera y rol del usuario
      Long heladeraId = Long.parseLong(context.pathParam("heladeraId"));
      String rol = context.sessionAttribute("rol");

      // Buscar la heladera en el repositorio
      Optional<Heladera> heladeraOpt = repositorioHeladera.buscarPorId(heladeraId, Heladera.class);

      // Verificar si la heladera existe
      if (heladeraOpt.isEmpty()) {
        context.status(404).result("Heladera no encontrada");
        return;
      }

      // Preparar el modelo para la vista
      Heladera heladera = heladeraOpt.get();
      Map<String, Object> model = new HashMap<>();
      model.put("heladera", heladera);
      model.put("jsonHeladera", gson.toJson(heladera));

      // Configurar la visualización según el rol
      if (TipoRol.valueOf(rol).equals(TipoRol.PERSONA_HUMANA)) {
        model.put("mostrarPersonaHumana", true);
      } else {
        model.put("mostrarPersonaJuridica", true);
      }

      // Renderizar la vista con el modelo preparado
      context.render(rutaParticularHbs, model);

    } catch (NumberFormatException e) {
      context.status(400).result("ID de heladera inválido");
    } catch (Exception e) {
      e.printStackTrace();
      context.status(500).result("Error interno del servidor");
    }
  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();
    Long id = context.sessionAttribute("id");
    model.put("jsonHeladeras", gson.toJson(this.repositorioHeladera.buscarTodos(Heladera.class)));
    model.put("modelos", this.repositorioHeladera.buscarTodos(Modelo.class));
    model.put("id", id);
    context.render(this.rutaAltaHbs, model);
  }

  @Override
  public void save(Context context) {
    HeladeraDTO dto = new HeladeraDTO();
    dto.obtenerFormulario(context);
    Long id = context.sessionAttribute("id");

    try {
      String idModelo = context.formParam("modelo");
      if (idModelo == null || idModelo.isEmpty()) {
        throw new ValidacionFormularioException("El modelo de la heladera es obligatorio y determina el rango de temperaturas válidas.");
      }

      Modelo modelo = this.repositorioHeladera
          .buscarPorId(Long.parseLong(idModelo), Modelo.class)
          .orElseThrow(() -> new ValidacionFormularioException("No se ha encontrado el modelo indicado."));

      dto.setTemperaturaMinima(modelo.getTemperaturaMinima());
      dto.setTemperaturaMaxima(modelo.getTemperaturaMaxima());

      Heladera nuevaHeladera = Heladera.fromDTO(dto);
      nuevaHeladera.setFechaRegistro(LocalDateTime.now());
      nuevaHeladera.setEstado(EstadoHeladera.ACTIVA);
      nuevaHeladera.setModelo(modelo);

      PersonaJuridica personaJuridica = this.repositorioPersonaJuridica
          .buscarPorUsuario(id)
          .orElseThrow(() -> new ValidacionFormularioException("No se ha podido encontrar la persona jurídica responsable."));

      personaJuridica.agregarHeladera(nuevaHeladera);

      withTransaction(() -> {
        repositorioHeladera.guardar(nuevaHeladera);
        repositorioPersonaJuridica.actualizar(personaJuridica);
      });

      context.redirect("/heladeras");

    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      model.put("id", id);
      model.put("modelos", this.repositorioHeladera.buscarTodos(Modelo.class));
      model.put("jsonHeladeras", gson.toJson(this.repositorioHeladera.buscarTodos(Heladera.class)));
      context.render(rutaAltaHbs, model);
    }
  }

  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<Heladera> heladera = this.repositorioHeladera.buscarPorId(Long.valueOf(context.pathParam("id")), Heladera.class);

      if (heladera.isEmpty()) {
        throw new ValidacionFormularioException("No existe un técnico con este id.");
      }

      HeladeraDTO dto = new HeladeraDTO(heladera.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaAltaHbs, model);
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      context.render("", model);
    }
  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<Heladera> heladera = this.repositorioHeladera.buscarPorId(id, Heladera.class);
    if (heladera.isPresent()) {
      withTransaction(() -> this.repositorioHeladera.eliminarFisico(Heladera.class, id));
      context.redirect("/heladeras");
    } else {
      context.status(400).result("No se puede eliminar, la heladera no cumple con las condiciones para ser eliminada.");
    }
  }

  public void obtenerMunicipiosPorProvincia(Context context) {
    String provincia = context.formParam("provincia");

    if (provincia == null || provincia.isEmpty()) {
      context.status(400).result("La provincia es obligatoria.");
      return;
    }

    List<Municipio> municipios = this.repositorioGeoRef.buscarMunicipiosDe(provincia);

    if (municipios == null || municipios.isEmpty()) {
      context.status(404).result("No se encontraron municipios para la provincia seleccionada.");
      return;
    }

    StringBuilder municipiosHtml = new StringBuilder();
    municipiosHtml.append("<option value='' selected>Municipio</option>");
    for (Municipio municipio : municipios) {
      municipiosHtml.append("<option value='").append(municipio.getMunicipio()).append("'>")
          .append(municipio.getMunicipio()).append("</option>");
    }

    context.result(municipiosHtml.toString());
  }

  public void recomendacion(Context context) {
    Map<String, Object> model = new HashMap<>();
    Long id = context.sessionAttribute("id");
    model.put("jsonHeladeras", gson.toJson(this.repositorioHeladera.buscarTodos(Heladera.class)));
    model.put("id", id);
    context.render(this.rutaRecomendacionHbs, model);
  }
}
