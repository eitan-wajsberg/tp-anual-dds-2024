package ar.edu.utn.frba.dds.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccesoDenegadoException extends RuntimeException {
  private int statusCode;
  public AccesoDenegadoException(String mensaje, int statusCode) {
    super(mensaje);
    this.statusCode = statusCode;
  }
}
