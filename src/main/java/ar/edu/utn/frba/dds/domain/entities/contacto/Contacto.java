package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.mail.MessagingException;

import ar.edu.utn.frba.dds.domain.repositories.IRepositorioMensajes;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Contacto {
  private Set<MedioDeContacto> mediosDeContacto;
  @Setter
  private static IRepositorioMensajes repositorioMensajes;

  private String telefono;
  private String celular;
  private String mail;
  private String userIDTelegram;

  public Contacto() {
    this.mediosDeContacto = new HashSet<>();
  }

  public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
    mediosDeContacto.add(medioDeContacto);
  }

  public void quitarMedioDeContacto(MedioDeContacto medioDeContacto) {
    mediosDeContacto.remove(medioDeContacto);
  }

  public void enviarMensaje(Mensaje mensaje) throws MessagingException, UnsupportedEncodingException {
    List<MedioDeContacto> medios = new ArrayList<>();
    medios.addAll(this.mediosDeContacto);
    for(MedioDeContacto medio: medios){
      medio.enviar(mensaje, this);
    }
    // repositorioMensajes.guardar(mensaje); // guardamos todos los mensajes o solo los que eran por las notif de eventos?
  }

  @Override
  public boolean equals(Object o){
    if (o == this) {
      return true;
    }

    if (!(o instanceof Contacto)) {
      return false;
    }

    Contacto contacto = (Contacto) o;

    return this.telefono.equals(contacto.telefono)
        && this.celular.equals(contacto.celular)
        && this.mail.equals(contacto.mail)
        && this.userIDTelegram.equals(contacto.userIDTelegram);
  }
}