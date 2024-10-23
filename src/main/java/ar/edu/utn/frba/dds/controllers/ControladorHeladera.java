package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Modelo;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Municipio;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Provincia;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef.GeoRefServicio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioGeoRef;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.dtos.HeladeraDTO;
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
  private RepositorioGeoRef repositorioGeoRef;
  private final String rutaAltaHbs = "colaboraciones/cuidarHeladera.hbs";
  private final Gson gson = GsonFactory.createGson();


  public ControladorHeladera(RepositorioHeladera repositoriaHeladera, RepositorioGeoRef repositorioGeoRef) {
    this.repositorioHeladera = repositoriaHeladera;
    this.repositorioGeoRef = repositorioGeoRef;
  }

  @Override
  public void index(Context context) {
    // tiene sentido? si ya estan plasmadas en el mapa
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();
    model.put("jsonHeladeras", gson.toJson(this.repositorioHeladera.buscarTodos(Heladera.class)));
    model.put("modelos", this.repositorioHeladera.buscarTodos(Modelo.class));
    model.put("provincias", this.repositorioGeoRef.buscarTodos(Provincia.class));
    context.render(this.rutaAltaHbs, model);
  }

  @Override
  public void save(Context context) {
    HeladeraDTO dto = new HeladeraDTO();
    dto.obtenerFormulario(context);
    Heladera nuevaHeladera;

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
      withTransaction(() -> repositorioHeladera.guardar(nuevaHeladera));

      context.redirect("/mapaHeladeras");
    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      context.render(rutaAltaHbs, model);
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
