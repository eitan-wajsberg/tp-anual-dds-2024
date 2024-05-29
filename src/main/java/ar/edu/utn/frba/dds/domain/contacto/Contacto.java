package ar.edu.utn.frba.dds.domain.contacto;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import javax.mail.MessagingException;
import lombok.Getter;

@Getter
public class Contacto {
  private Set<MedioDeContacto> mediosDeContacto;

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
      medio.enviar(mensaje);
    }
  }
}