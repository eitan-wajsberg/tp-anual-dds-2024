package ar.edu.utn.frba.dds.domain.validador;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidadorDeClave {
  private Set<TipoValidacion> validadores;

  public ValidadorDeClave(){
    validadores = new HashSet<>();
  }

  public boolean validar(String clave) {
    String errores = "";
    boolean esValido = true;

    for (TipoValidacion validador : validadores) {
      if (!validador.validar(clave)) {
        errores += validador.getMensajeError() + '\n';
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
