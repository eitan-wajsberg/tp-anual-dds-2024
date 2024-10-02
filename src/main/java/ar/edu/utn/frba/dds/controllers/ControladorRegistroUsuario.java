package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorRegistroUsuario implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private Repositorio repositorioUsuario;
  private Repositorio repositorioRol;

  public ControladorRegistroUsuario(Repositorio repositorioUsuario, Repositorio repositorioRol) {
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRol = repositorioRol;
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
    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    if (tipoCuenta == null) {
      context.redirect("/");
      return;
    }
    System.out.println(tipoCuenta);

    Map<String, Object> model = new HashMap<>();
    if (tipoCuenta.equals("2")) {
      model.put("personaHumana", true);
    }

    context.render("/cuenta/crearCuenta.hbs", model);
  }


  @Override
  public void save(Context context) {
    Usuario usuario = new Usuario();

    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    if (tipoCuenta == null) {
      context.redirect("/");
      return;
    }

    usuario.setNombre(context.formParam("usuario"));
    usuario.setClave(context.formParam("clave"));

    withTransaction(() -> {
      Optional<Rol> rol = this.repositorioRol.buscarPorId(Long.parseLong(tipoCuenta), Rol.class);
      rol.ifPresent(usuario::setRol);
      this.repositorioUsuario.guardar(usuario);
    });

    context.redirect("");
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