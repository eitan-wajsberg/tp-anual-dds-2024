package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import lombok.Setter;

@Setter
public class Mail implements MedioDeContacto {
  private AdapterMail adaptador;

  @Override
  public void enviar(Mensaje mensaje, Contacto contacto) throws MessagingException, UnsupportedEncodingException {
    adaptador.enviar(mensaje, contacto.getMail());
  }

}
