package ar.edu.utn.frba.dds.domain.definirPackages;

import lombok.Getter;
import lombok.Setter;

public class WhatsApp implements MedioDeContacto {

  public String numero;

  @Override
  public void enviar(Mensaje mensaje) {
    //TODO:
  }
}
