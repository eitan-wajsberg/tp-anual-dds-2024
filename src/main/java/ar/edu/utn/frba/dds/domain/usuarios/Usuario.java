package ar.edu.utn.frba.dds.domain.usuarios;

import ar.edu.utn.frba.dds.domain.validador.ValidadorDeClave;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Usuario {
  @Setter
  private String nombre;
  private String clave;
  @Setter
  private Rol rol;

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void cambiarClave(String clave, ValidadorDeClave validador) throws RuntimeException {
    String claveAuxiliar = this.clave;
    this.clave = clave;
    if (!validador.validar(clave)) {
      this.clave = claveAuxiliar;
    }
  }

}