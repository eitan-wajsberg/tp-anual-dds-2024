package ar.edu.utn.frba.dds.domain.adapters;

import ar.edu.utn.frba.dds.domain.contacto.Mensaje;
import javax.mail.MessagingException;

public interface AdapterMail {
  public void enviar(Mensaje mensaje, String correo) throws MessagingException;
}
