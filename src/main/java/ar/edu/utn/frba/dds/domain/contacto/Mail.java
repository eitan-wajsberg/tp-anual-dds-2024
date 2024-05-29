package ar.edu.utn.frba.dds.domain.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import lombok.Setter;

public class Mail implements MedioDeContacto {

  private String correo;
  @Setter
  private AdapterMail adaptador;

  public Mail(String correo){
    this.correo = correo;
  }

  @Override
  public void enviar(Mensaje mensaje) throws MessagingException, UnsupportedEncodingException {
    adaptador.enviar(mensaje, correo);
  }
}
