package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorRegistroUsuario implements WithSimplePersistenceUnit {
  private Repositorio repositorioUsuario;
  private Repositorio repositorioRol;

  public ControladorRegistroUsuario(Repositorio repositorioUsuario, Repositorio repositorioRol) {
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRol = repositorioRol;
  }

  public void create(Context context) {
    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    if (tipoCuenta == null) {
      context.redirect("");
      return;
    }
    System.out.println(tipoCuenta);

    Map<String, Object> model = new HashMap<>();
    if (tipoCuenta.equals("2")) {
      model.put("personaHumana", true);
    }

    context.render("/cuenta/crearCuenta.hbs", model);
  }


  public void save(Context context) {
    Usuario usuario = new Usuario();

    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    if (tipoCuenta == null) {
      context.redirect("/");
      return;
    }

    usuario.setNombre(context.formParam("usuario"));

    // TODO: COMPLETAR Y MEJORAR

    // utilizar el validador de contraseñas
    usuario.setClave(context.formParam("clave"));

    withTransaction(() -> {
      Optional<Rol> rol = this.repositorioRol.buscarPorId(Long.parseLong(tipoCuenta), Rol.class);
      rol.ifPresent(usuario::setRol);
      this.repositorioUsuario.guardar(usuario);
    });

    // redireccionar al login?
    context.redirect("/");
  }
}