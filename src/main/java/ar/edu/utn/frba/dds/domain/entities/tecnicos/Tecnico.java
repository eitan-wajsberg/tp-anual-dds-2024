package ar.edu.utn.frba.dds.domain.entities.tecnicos;

import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@Entity @Table(name="tecnico")
@NoArgsConstructor
public class Tecnico {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name="nombre")
  private String nombre;

  @Column(name="apellido")
  private String apellido;


  @Column(name="tipoDocumento")
  private TipoDocumento tipoDocumento;

  @Column(name="nroDocumento")
  private String nroDocumento;

  @Column(name="cuil")
  private String cuil;

  @OneToOne
  @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private Contacto contacto;

  @Embedded
  private Coordenada coordenada;

  @Column(name="distanciaMaximaEnKMParaSerAvisado")
  private double distanciaMaximaEnKmParaSerAvisado;

  public Tecnico(Contacto contacto) {
    this.contacto = contacto;
  }
}
