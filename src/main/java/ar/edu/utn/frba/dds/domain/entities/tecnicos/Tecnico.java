package ar.edu.utn.frba.dds.domain.entities.tecnicos;

import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class Tecnico {
  private String nombre;
  private String apellido;
  private String tipoDocumento;
  private String nroDocumento;
  private String cuil;
  private Contacto contacto;
  private Coordenada coordenada;
  private double distanciaMaximaEnKmParaSerAvisado;

  public Tecnico(Contacto contacto) {
    this.contacto = contacto;
  }

}
