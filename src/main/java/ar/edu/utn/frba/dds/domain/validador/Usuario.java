package ar.edu.utn.frba.dds.domain.validador;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Usuario {
  private String nombre;
  private String secretoMemorizado;

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void cambiarSecreto(String secreto, ValidadorDeSecretosMemorizados validador) throws RuntimeException {
    String secretoAuxiliar = this.secretoMemorizado;
    this.setSecretoMemorizado(secreto);
    if(!validador.validar(this.nombre,this.secretoMemorizado)) {
      this.setSecretoMemorizado(secretoAuxiliar);
    }
  }

}
