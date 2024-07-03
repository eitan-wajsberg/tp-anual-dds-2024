package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterWhatsApp;

public class WhatsApp implements MedioDeContacto {

  private AdapterWhatsApp adaptador;
  private String numero; //Entiendo que lo debería obtener de Usuario?

  @Override
  public void enviar(Mensaje mensaje) {
    adaptador.enviar(numero, mensaje.getCuerpo());
  }
}
