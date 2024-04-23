package ar.edu.utn.frba.dds.domain.validador;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LongitudEstipulada {
  private int longitudMaxima;
  private int longitudMinima;
    public boolean validar(Usuario usuario) {
    int longitud = usuario.getSecretoMemorizado().length();
    return (longitud >= longitudMinima && longitud <= longitudMaxima);
    }
}
//TODO: Hacer la logica de las excepciones