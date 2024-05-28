package ar.edu.utn.frba.dds.domain.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;

public class Mail implements MedioDeContacto {

  private String correo;
  private AdapterMail adaptador;

  public Mail(String correo){
    this.correo = correo;
  }

  @Override
  public void enviar(Mensaje mensaje) {

  }
}
