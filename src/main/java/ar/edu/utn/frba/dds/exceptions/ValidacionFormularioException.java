package ar.edu.utn.frba.dds.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidacionFormularioException extends RuntimeException {
  private String rutaHandlebar;

  public ValidacionFormularioException(String mensaje, String rutaHandlebar) {
    super(mensaje);
    this.rutaHandlebar = rutaHandlebar;
  }
}
