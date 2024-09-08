package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterTelegram;

public class Telegram implements MedioDeContacto {

  private AdapterTelegram adaptador;

  @Override
  public void enviar(Mensaje mensaje, Contacto contacto) {
    adaptador.enviar(mensaje.getCuerpo());
  }
  // TODO en qué momento está aclarando que le envía el mensaje a ese contacto en específico? jaja
}