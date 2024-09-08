package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterWhatsApp;

public class WhatsApp implements MedioDeContacto {

  private AdapterWhatsApp adaptador;

  @Override
  public void enviar(Mensaje mensaje, Contacto contacto) {
    adaptador.enviar(contacto.getCelular(), mensaje.getCuerpo());
  }
}
