package ar.edu.utn.frba.dds.domain.validador;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class ValidadorDeSecretosMemorizados {
  @Getter @Setter
  private Set<TipoValidacion> validadores;

  public boolean validar(Usuario usuario) {
    /*
      utilizo reduce para evaluar cada validador
      Alternativa sin evaluar todos
      return this.validadores.stream().allMatch(validador -> validador.validar(usuario))
    */
    return this.validadores.stream().reduce(true,
        (resultadoParcial, validador) ->
        resultadoParcial && validador.validar(usuario),
        Boolean::logicalAnd
        );

    }

}
//TODO: