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

  @Override
  public boolean validar(Usuario usuario) {
    int longitud = usuario.getSecretoMemorizado().length();
    return (longitud >= longitudMinima && longitud <= longitudMaxima);
  }
  @Override
  public String getMensajeError() {
    return "La longitud del secreto debe estar entre 8 y 64 caracteres";
  }

}
