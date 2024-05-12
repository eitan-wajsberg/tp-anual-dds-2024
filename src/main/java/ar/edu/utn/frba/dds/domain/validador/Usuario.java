package ar.edu.utn.frba.dds.domain.validador;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {
  private String nombre;
  private String clave;

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void cambiarClave(String clave, ValidadorDeClave validador) throws RuntimeException {
    String claveAuxiliar = this.clave;
    this.setClave(clave);
    if (!validador.validar(this.nombre, this.clave)) {
      this.setClave(claveAuxiliar);
    }
  }

}
