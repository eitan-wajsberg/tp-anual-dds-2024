package ar.edu.utn.frba.dds.domain.entities.contacto;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.mail.MessagingException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embeddable
public class Contacto {
  @Transient
  private Set<MedioDeContacto> mediosDeContacto;

  @Column(name = "whatsapp")
  private String whatsapp;
  @Setter
  @Column(name = "mail")
  private String mail;

  @Column(name = "userTelegram")
  private Long telegramChatId;

  @Column(name = "medioPreferido")
  private String medioPreferido;

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

    return this.whatsapp.equals(contacto.whatsapp)
        && this.mail.equals(contacto.mail)
        && this.telegramChatId.equals(contacto.telegramChatId);
  }
}