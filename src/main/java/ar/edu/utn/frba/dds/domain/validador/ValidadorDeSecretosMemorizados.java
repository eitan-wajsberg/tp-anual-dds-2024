package ar.edu.utn.frba.dds.domain.validador;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidadorDeSecretosMemorizados {
  private Set<TipoValidacion> validadores;

  public boolean validar(Usuario usuario) {
    String errores = "";
    boolean esValido = true;

    for (TipoValidacion validador : validadores) {
      if (!validador.validar(usuario)) {
        errores += validador.getMensajeError();
        esValido = false;
      }
    }
    final String erroresFinales = errores;

    if (!esValido) {
      throw new RuntimeException(erroresFinales);
    }

    return esValido;
  }
}
