package ar.edu.utn.frba.dds.dtos;

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
}
