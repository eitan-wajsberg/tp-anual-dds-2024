package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import io.javalin.http.Context;
import lombok.Data;

@Data
public class ContactoDTO implements DTO {
  private String whatsapp;
  private String telegram;
  private String correo;
  private String rutaHbs;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setWhatsapp(context.formParam("whatsapp"));
    this.setTelegram(context.formParam("telegram"));
    this.setCorreo(context.formParam("correo"));
    this.setRutaHbs(rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if ((this.whatsapp == null || this.whatsapp.isEmpty())
        && (this.telegram == null || this.telegram.isEmpty())
        && (this.correo == null || this.correo.isEmpty())) {
      throw new ValidacionFormularioException("Por favor, indique al menos un medio de contacto.", rutaHbs);
    }

    if (this.correo != null && !this.correo.isEmpty()
        && !this.correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
      throw new ValidacionFormularioException("El formato del correo electrónico es inválido.", rutaHbs);
    }

    Contacto contacto = new Contacto();
    contacto.setMail(this.correo);

    if (this.telegram != null && !this.telegram.isEmpty()) {
      try {
        contacto.setTelegramChatId(Long.valueOf(this.telegram));
      } catch (NumberFormatException e) {
        throw new ValidacionFormularioException("El formato del Telegram ID es inválido.", rutaHbs);
      }
    }

    contacto.setWhatsapp(this.whatsapp);

    return contacto;
  }
}
