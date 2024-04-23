package ar.edu.utn.frba.dds.domain.validador;

import static ar.edu.utn.frba.dds.domain.validador.Main.validador;

import lombok.Getter;
import lombok.Setter;

public class Usuario {
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private String secretoMemorizado;

  public void cambiarSecreto(String secreto) {
    String secretoAuxiliar = this.secretoMemorizado;
    this.setSecretoMemorizado(secreto);
    if(!validador.validar(this)) {
    // excepción de secreto invalido
    this.setSecretoMemorizado(secretoAuxiliar);
    }
  }

}
