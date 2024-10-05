package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioUsuario;
import ar.edu.utn.frba.dds.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.ValidadorRegistroException;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorRegistroUsuario implements WithSimplePersistenceUnit {
  private RepositorioUsuario repositorioUsuario;
  private Repositorio repositorioRol;

  public ControladorRegistroUsuario(RepositorioUsuario repositorioUsuario, Repositorio repositorioRol) {
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRol = repositorioRol;
  }

  public void create(Context context) {
    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    if (tipoCuenta == null) {
      context.redirect("");
      return;
    }

    Map<String, Object> model = new HashMap<>();
    if (tipoCuenta.equals("2")) {
      model.put("personaHumana", true);
    }

    context.render("/cuenta/crearCuenta.hbs", model);
  }

  public void save(Context context) {
    UsuarioDTO dto = new UsuarioDTO();
    dto.setNombre(context.formParam("usuario"));
    dto.setClave(context.formParam("clave"));
    dto.setClaveRepetida(context.formParam("claveRepetida"));
    dto.setRol(context.sessionAttribute("tipoCuenta"));

    Usuario usuario = dto.toUsuario();

    if (usuario != null) {
      if (this.repositorioUsuario.existeUsuarioPorNombre(usuario.getNombre())) {
        throw new ValidadorRegistroException("El nombre de usuario ya está en uso. Por favor, elige uno diferente.");
      }

      Optional<Rol> rol = this.repositorioRol.buscarPorId(Long.parseLong(dto.getRol()), Rol.class);
      if (rol.isEmpty()) {
        throw new ValidadorRegistroException("El rol indicado no existe. Por favor, elige uno diferente.");
      }

      usuario.setRol(rol.get());

      withTransaction(() -> {
        this.repositorioUsuario.guardar(usuario);
      });
    } else {
      throw new ValidadorRegistroException("Los datos del usuario son inválidos.");
    }

    // Redireccionar al login si todo salió bien
    context.redirect("/");
  }
}