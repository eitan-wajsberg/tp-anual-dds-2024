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

  @Override
  public void show(Context context) {
    try {
      String rol = context.sessionAttribute("rol");
      Optional<Heladera> heladera = repositorioHeladera.buscarPorId(Long.parseLong(context.pathParam("heladeraId")), Heladera.class);
      if (heladera.isPresent()) {
        String jsonHeladera = gson.toJson(heladera.get());
        Map<String, Object> model = new HashMap<>();
        model.put("heladeraId",context.pathParam("heladeraId"));
        model.put("heladera", heladera.get());
        model.put("jsonHeladera", jsonHeladera);

        if (TipoRol.valueOf(rol).equals(TipoRol.PERSONA_HUMANA)) {
          model.put("mostrarPersonaHumana", true);
        } else {
          model.put("mostrarPersonaJuridica", true);
        }
        context.render(rutaParticularHbs, model);
      } else {
        context.status(404).result("Heladera no encontrada"); // FIXME
      }
    } catch (Exception e) {
      e.printStackTrace();
      context.status(500).result("Error interno del servidor"); // FIXME
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
    Heladera nuevaHeladera;
    Long id = context.sessionAttribute("id");

    try {
      nuevaHeladera = Heladera.fromDTO(dto);
      if (nuevaHeladera == null) {
        throw new ValidacionFormularioException("Los datos de la heladera son inválidos.");
      }

      String idModelo = context.formParam("modelo");
      if (idModelo != null && !idModelo.isEmpty()) {
        Optional<Modelo> modelo = this.repositorioHeladera.buscarPorId(Long.parseLong(idModelo), Modelo.class);
        modelo.ifPresent(nuevaHeladera::setModelo);
      }

      nuevaHeladera.setFechaRegistro(LocalDateTime.now());
      nuevaHeladera.setEstado(EstadoHeladera.ACTIVA);

      Optional<PersonaJuridica> personaJuridica = this.repositorioPersonaJuridica.buscarPorUsuario(id);
      if (personaJuridica.isEmpty()) {
        throw new ValidacionFormularioException("No se ha podido encontrar la persona juridica responsable.");
      }
      personaJuridica.get().agregarHeladera(nuevaHeladera);

      withTransaction(() -> {
        repositorioHeladera.guardar(nuevaHeladera);
        repositorioPersonaJuridica.actualizar(personaJuridica.get());
      });

      context.redirect("/mapaHeladeras");
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      model.put("id", id);
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
      context.redirect("/mapaHeladeras");
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
}
