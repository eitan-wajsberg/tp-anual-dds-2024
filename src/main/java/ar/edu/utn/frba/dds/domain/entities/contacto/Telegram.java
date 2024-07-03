package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterTelegram;

public class Telegram implements MedioDeContacto {

  private AdapterTelegram adaptador;
  //private String chatId;

  @Override
  public void enviar(Mensaje mensaje) {
    adaptador.enviar(mensaje.getCuerpo());
  }
}