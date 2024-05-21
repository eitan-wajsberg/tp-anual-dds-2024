package ar.edu.utn.frba.dds.domain.definirPackages;

import lombok.Getter;
import lombok.Setter;

public class Mail implements MedioDeContacto {

  private String correo;
  private AdapterMail adaptador;

  @Override
  public void enviar(Mensaje mensaje) {

  }
}
