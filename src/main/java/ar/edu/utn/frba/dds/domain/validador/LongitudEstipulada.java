package ar.edu.utn.frba.dds.domain.validador;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LongitudEstipulada implements TipoValidacion {
  private int longitudMaxima;
  private int longitudMinima;

  public LongitudEstipulada(int longitudMaxima, int longitudMinima) {
    this.longitudMaxima = longitudMaxima;
    this.longitudMinima = longitudMinima;
  }

  public boolean validar(Usuario usuario) {
    int longitud = usuario.getSecretoMemorizado().length();
    return (longitud >= longitudMinima && longitud <= longitudMaxima);
  }
}
//TODO: Hacer la logica de las excepciones