package ar.edu.utn.frba.dds.domain.contacto;

import java.util.Set;
import java.util.HashSet;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
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
}