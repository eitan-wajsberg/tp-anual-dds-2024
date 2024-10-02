package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ControladorPersonaVulnerable implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private Repositorio repositorioPersonaVulnerable;
  private RepositorioPersonaHumana repositorioPersonaHumana;

  public ControladorPersonaVulnerable(Repositorio repositorioPersonaVulnerable, RepositorioPersonaHumana repositorioPersonaHumana) {
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
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
    context.render("colaboraciones/registroPersonaVulnerable.hbs");
  }

  @Override
  public void save(Context context) {
    PersonaVulnerable nuevaPersona = new PersonaVulnerable();

    nuevaPersona.setNombre(context.formParam("nombre"));
    nuevaPersona.setApellido(context.formParam("apellido"));
    nuevaPersona.setFechaDeNacimiento(LocalDate.parse(Objects.requireNonNull(context.formParam("fecha"))));
    nuevaPersona.setMenoresAcargo(Integer.parseInt(Objects.requireNonNull(context.formParam("cantidadMenores"))));
    nuevaPersona.setFechaDeRegistro(LocalDate.now());

    Optional<PersonaHumana> registrador = repositorioPersonaHumana.buscarPorId(1L, PersonaHumana.class);
    registrador.ifPresent(nuevaPersona::setPersonaQueLoRegistro);

    Direccion direccion = new Direccion();
    // direccion.normalizar("Cabildo y Juramento 500");

    nuevaPersona.setDireccion(direccion);
    nuevaPersona.setNroDocumento(context.formParam("nroDocumento"));
    nuevaPersona.setTipoDocumento(TipoDocumento.valueOf(context.formParam("tipoDocumento")));

    withTransaction(() -> {
      this.repositorioPersonaVulnerable.guardar(nuevaPersona);
    });

    context.redirect("/inicio");
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