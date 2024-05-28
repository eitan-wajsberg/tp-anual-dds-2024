package ar.edu.utn.frba.dds.domain.tecnicos;

import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
public class Tecnico {
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private String apellido;
  @Getter @Setter
  private String tipoDocumento;
  @Getter @Setter
  private String nroDocumento;
  @Getter @Setter
  private String cuil;
  @Getter @Setter
  private Contacto contacto;
  @Getter
  private Set<Area> areasDeCobertura;

  public Tecnico(Contacto contacto) {
    this.contacto = new Contacto();
    this.areasDeCobertura = new HashSet<>();
  }

  public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
     this.contacto.agregarMedioDeContacto(medioDeContacto);
  }

  public void quitarMedioDeContacto(MedioDeContacto medioDeContacto) {
    this.contacto.quitarMedioDeContacto(medioDeContacto);
  }



}
