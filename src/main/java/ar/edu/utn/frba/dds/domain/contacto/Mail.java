package ar.edu.utn.frba.dds.domain.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;

public class Mail implements MedioDeContacto {

  private String correo;
  private AdapterMail adaptador;

  @Override
  public void enviar(Mensaje mensaje) {

  }
}
