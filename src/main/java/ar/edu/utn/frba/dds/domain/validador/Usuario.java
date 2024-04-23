package ar.edu.utn.frba.dds.domain.validador;

import static ar.edu.utn.frba.dds.domain.validador.Main.validador;

import lombok.Getter;
import lombok.Setter;

public class Usuario {
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private String secretoMemorizado;

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void cambiarSecreto(String secreto, ValidadorDeSecretosMemorizados validador) throws RuntimeException{
    String secretoAuxiliar = this.secretoMemorizado;
    this.setSecretoMemorizado(secreto);
    if(!validador.validar(this)) {
      this.setSecretoMemorizado(secretoAuxiliar);
    }
  }

}
