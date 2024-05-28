package ar.edu.utn.frba.dds.domain.contacto;

import javax.mail.MessagingException;

public interface MedioDeContacto {

  void enviar(Mensaje mensaje) throws MessagingException;
}
