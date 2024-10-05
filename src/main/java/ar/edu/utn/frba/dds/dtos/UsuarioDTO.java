package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.entities.validador.AusenciaDeCredencialesPorDefecto;
import ar.edu.utn.frba.dds.domain.entities.validador.ListaDePeoresClavesMemorizadas;
import ar.edu.utn.frba.dds.domain.entities.validador.LongitudEstipulada;
import ar.edu.utn.frba.dds.domain.entities.validador.ValidadorDeClave;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.javalin.http.Context;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

@Data
public class UsuarioDTO implements DTO {
  private String nombre;
  private String clave;
  private String claveRepetida;
  private String rol;
  private String rutaHbs;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setNombre(context.formParam("usuario"));
    this.setClave(context.formParam("clave"));
    this.setClaveRepetida(context.formParam("claveRepetida"));
    this.setRol(context.sessionAttribute("tipoCuenta"));
    this.setRutaHbs(rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if (this.nombre.isEmpty() || this.clave.isEmpty() || this.claveRepetida.isEmpty() || this.rol.isEmpty()) {
      throw new ValidacionFormularioException("Por favor, complete todos los campos.", rutaHbs);
    }

    if (this.nombre.length() < 5 || this.nombre.length() > 50) {
      throw new ValidacionFormularioException(String.format(
          "La longitud del nombre de usuario debe estar entre 5 y 50 caracteres. Longitud actual: %d caracteres.",
          this.nombre.length()
      ), rutaHbs);
    }

    if (!this.clave.equals(this.claveRepetida)) {
      throw new ValidacionFormularioException("La clave y la confirmación de clave no coinciden. Asegúrate de ingresar la misma clave en ambos campos.", rutaHbs);
    }

    int longitud = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("longitud_maxima_clave"));
    ValidadorDeClave validador = new ValidadorDeClave(
        new LongitudEstipulada(longitud),
        new ListaDePeoresClavesMemorizadas(),
        new AusenciaDeCredencialesPorDefecto(this.nombre)
    );
    if (!validador.validar(clave)) {
      throw new ValidacionFormularioException(validador.getErroresFinales(), rutaHbs);
    }

    Usuario usuario = new Usuario();
    usuario.setNombre(this.nombre);
    String claveEncriptada = BCrypt.hashpw(this.clave, BCrypt.gensalt());
    usuario.setClave(claveEncriptada);

    return usuario;
  }
}
