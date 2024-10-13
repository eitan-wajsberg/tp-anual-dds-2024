package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.dtos.ContactoDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
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
@Setter
@Embeddable
public class Contacto {
  @Transient
  private Set<MedioDeContacto> mediosDeContacto;

  @Column(name = "whatsapp")
  private String whatsapp;

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
    List<MedioDeContacto> medios = new ArrayList<>(this.mediosDeContacto);
    for (MedioDeContacto medio : medios) {
      medio.enviar(mensaje, this);
    }
  }

  public static Contacto fromDTO(ContactoDTO dto) {
    validarContacto(dto);

    Contacto contacto = new Contacto();
    contacto.setWhatsapp(dto.getWhatsapp());
    contacto.setMail(dto.getCorreo());

    if (dto.getTelegram() != null && !dto.getTelegram().isEmpty()) {
      contacto.setTelegramChatId(Long.valueOf(dto.getTelegram()));
    }

    return contacto;
  }

  private static void validarContacto(ContactoDTO dto) {
    if ((dto.getWhatsapp() == null || dto.getWhatsapp().isEmpty())
        && (dto.getTelegram() == null || dto.getTelegram().isEmpty())
        && (dto.getCorreo() == null || dto.getCorreo().isEmpty())) {
      throw new ValidacionFormularioException("Por favor, indique al menos un medio de contacto.");
    }

    if (dto.getCorreo() != null && !dto.getCorreo().isEmpty()
        && !dto.getCorreo().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
      throw new ValidacionFormularioException("El formato del correo electrónico es inválido.");
    }

    if (dto.getWhatsapp() != null && !dto.getWhatsapp().isEmpty()
        && !dto.getWhatsapp().matches("^[0-9]{10,15}$")) {
      throw new ValidacionFormularioException("El formato del número de WhatsApp es inválido.");
    }
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