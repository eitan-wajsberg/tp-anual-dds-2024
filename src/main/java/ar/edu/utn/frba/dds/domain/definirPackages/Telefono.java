package ar.edu.utn.frba.dds.domain.definirPackages;

import lombok.Getter;
import lombok.Setter;

public class Telefono implements MedioDeContacto {

  private String numero;

  @Override
  public void enviar(Mensaje mensaje) {
    //TODO:
  }
}
