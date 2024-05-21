package ar.edu.utn.frba.dds.domain.definirPackages;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class Tecnico {

  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private String nroDocumento;
  private String cuil;
  private Set<MedioDeContacto> mediosDeContacto;
  private Set<Area> areasDeCobertura;

  public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
    //TODO:
  }

  public void quitarMedioDeContacto(MedioDeContacto medioDeContacto) {
    //TODO:
  }

}
