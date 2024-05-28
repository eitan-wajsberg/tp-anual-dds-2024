package ar.edu.utn.frba.dds.domain.tecnicos;

import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
@Getter
public class Tecnico {
   @Setter
  private String nombre;
   @Setter
  private String apellido;
   @Setter
  private String tipoDocumento;
   @Setter
  private String nroDocumento;
   @Setter
  private String cuil;
   @Setter
  private Contacto contacto;
  private Set<Area> areasDeCobertura;

  public Tecnico(Contacto contacto) {
    this.contacto = contacto;
    this.areasDeCobertura = new HashSet<>();
  }
}
