package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.entities.validador.ValidadorDeClave;
import ar.edu.utn.frba.dds.exceptions.ValidadorRegistroException;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

@Data
public class UsuarioDTO {
  private String nombre;
  private String clave;
  private String claveRepetida;
  private String rol;

  public Usuario toUsuario() {
    Usuario usuario = new Usuario();

    if (this.nombre.isEmpty() || this.clave.isEmpty() || this.claveRepetida.isEmpty() || this.rol.isEmpty()) {
      return null;
    }

    if (this.nombre.length() < 5 || this.nombre.length() > 50) {
      return null;
    }

    if (!this.clave.equals(this.claveRepetida)) {
      return null;
    }

    ValidadorDeClave validador = ServiceLocator.instanceOf(ValidadorDeClave.class);
    if (!validador.validar(clave)) {
      String mensajeError = validador.getErroresFinales().concat(" " + clave);
      throw new ValidadorRegistroException(mensajeError);
    }

    usuario.setNombre(this.nombre);
    String claveEncriptada = BCrypt.hashpw(this.clave, BCrypt.gensalt());
    usuario.setClave(claveEncriptada);

    return usuario;
  }
}
