package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoCuentaRegistro;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.dtos.HeladeraDTO;
import ar.edu.utn.frba.dds.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorHeladera implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioHeladera repositorioHeladera;
  private final String rutaAltaHbs = "colaboraciones/cuidarHeladera.hbs";

  public ControladorHeladera(RepositorioHeladera repositoriaHeladera) {
    this.repositorioHeladera = repositoriaHeladera;
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
    context.render(rutaAltaHbs);
  }

  @Override
  public void save(Context context) {
    HeladeraDTO dto = new HeladeraDTO();
    dto.obtenerFormulario(context);
    Heladera nuevaHeladera;

    try {
      nuevaHeladera = Heladera.fromDTO(dto);
      if (nuevaHeladera == null) {
        throw new ValidacionFormularioException("Los datos del técnico son inválidos.");
      }

      // TODO: Cargar / buscar modelo en la base
      // nuevaHeladera.setModelo();

      nuevaHeladera.setFechaRegistro(LocalDateTime.now());
      nuevaHeladera.setEstado(EstadoHeladera.ACTIVA);

      withTransaction(() -> {
        repositorioHeladera.guardar(nuevaHeladera);
      });

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
}
