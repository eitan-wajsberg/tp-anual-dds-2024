package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.Objects;

public class ControladorAltaTecnicos implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioTecnicos repositorioTecnicos;
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("hbs_alta_tecnicos");

  public ControladorAltaTecnicos(RepositorioTecnicos repositorioTecnico) {
    this.repositorioTecnicos = repositorioTecnico;
  }

  @Override
  public void index(Context context) {
    // TODO
  }

  @Override
  public void show(Context context) {
    // TODO
  }

  @Override
  public void create(Context context) {
    context.render(rutaHbs);
  }

  @Override
  public void save(Context context) {
    Tecnico nuevoTecnico = new Tecnico();

    nuevoTecnico.setNombre(context.formParam("nombre"));
    nuevoTecnico.setApellido(context.formParam("apellido"));
    nuevoTecnico.setDistanciaMaximaEnKmParaSerAvisado(Double.parseDouble(Objects.requireNonNull(context.formParam("distancia"))));

    context.formParam("direccion");
    Coordenada coordenada = new Coordenada();
    // direccion.normalizar("Cabildo y Juramento 500");

    // TODO: COMPLETAR Y MEJORAR

    nuevoTecnico.setCoordenada(coordenada);
    nuevoTecnico.setNroDocumento(context.formParam("nroDocumento"));
    nuevoTecnico.setTipoDocumento(TipoDocumento.valueOf(context.formParam("tipoDocumento")));

    withTransaction(() -> {
      this.repositorioTecnicos.guardar(nuevoTecnico);
    });

    context.redirect("/admin");
  }

  @Override
  public void edit(Context context) {
    // TODO
  }

  @Override
  public void update(Context context) {
    // TODO
  }

  @Override
  public void delete(Context context) {
    // TODO
  }
}
