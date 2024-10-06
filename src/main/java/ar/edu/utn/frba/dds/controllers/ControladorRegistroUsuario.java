package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoCuentaRegistro;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRol;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioUsuario;
import ar.edu.utn.frba.dds.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControladorRegistroUsuario implements WithSimplePersistenceUnit {
  private RepositorioUsuario repositorioUsuario;
  private RepositorioRol repositorioRol;
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("hbs_crear_cuenta");

  public ControladorRegistroUsuario(RepositorioUsuario repositorioUsuario, RepositorioRol repositorioRol) {
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRol = repositorioRol;
  }

  public void create(Context context) {
    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    if (tipoCuenta == null) {
      throw new ValidacionFormularioException("No se ha indicado un tipo de cuenta.", rutaHbs);
    }

    Map<String, Object> model = new HashMap<>();
    if (TipoCuentaRegistro.valueOf(tipoCuenta) == TipoCuentaRegistro.PERSONA_HUMANA) {
      model.put("personaHumana", true);
    }

    context.render(rutaHbs, model);
  }

  public void save(Context context) {
    UsuarioDTO dto = new UsuarioDTO();
    dto.obtenerFormulario(context, rutaHbs);
    Usuario usuario = (Usuario) dto.convertirAEntidad();

    if (usuario == null) {
      throw new ValidacionFormularioException("Los datos del usuario son inválidos.", rutaHbs);
    }

    if (repositorioUsuario.existeUsuarioPorNombre(usuario.getNombre())) {
      throw new ValidacionFormularioException("El nombre de usuario ya está en uso. Por favor, elige uno diferente.", rutaHbs);
    }

    Optional<Rol> rol = repositorioRol.buscarPorNombre(dto.getRol());
    if (rol.isEmpty()) {
      throw new ValidacionFormularioException("El rol indicado no existe. Por favor, elige uno diferente.", rutaHbs);
    }
    usuario.setRol(rol.get());


    withTransaction(() -> repositorioUsuario.guardar(usuario));

    // FIXME: Redireccionar al login si salió bien
    context.redirect("/");
  }
}