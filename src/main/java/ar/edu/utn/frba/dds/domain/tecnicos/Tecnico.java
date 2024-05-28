package ar.edu.utn.frba.dds.domain.tecnicos;

import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;

import java.util.Set;

public class Tecnico {

  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private String nroDocumento;
  private String cuil;
  private Contacto contacto;
  private Set<Area> areasDeCobertura;

  public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
     this.contacto.agregarMedioDeContacto(medioDeContacto);
  }

  public void quitarMedioDeContacto(MedioDeContacto medioDeContacto) {
    this.contacto.quitarMedioDeContacto(medioDeContacto);
  }

}
