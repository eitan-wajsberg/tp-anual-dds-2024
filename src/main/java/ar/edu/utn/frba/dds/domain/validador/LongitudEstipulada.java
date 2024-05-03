package ar.edu.utn.frba.dds.domain.validador;

import lombok.Getter;
import lombok.Setter;


@Getter
public class LongitudEstipulada implements TipoValidacion {
  @Setter
  private int longitudMaxima;
  private final int longitudMinima = 8;

  public LongitudEstipulada(int longitudMaxima) {
    this.longitudMaxima = longitudMaxima;
  }

  @Override
  public boolean validar(String nombreUsuario, String secreto) {
    int longitud = secreto.length();
    return (longitud >= longitudMinima && longitud <= longitudMaxima);
  }
  @Override
  public String getMensajeError() {
    return "La longitud del secreto debe estar entre 8 y " + longitudMaxima + " caracteres";
  }

}
