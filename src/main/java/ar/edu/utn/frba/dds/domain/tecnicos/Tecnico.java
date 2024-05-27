package ar.edu.utn.frba.dds.domain.tecnicos;

import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import java.util.Set;

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
