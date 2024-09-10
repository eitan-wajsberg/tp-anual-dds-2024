package ar.edu.utn.frba.dds.domain.entities.tecnicos;

import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
@Getter @Setter
@Entity @Table(name="tecnico")
@NoArgsConstructor
public class Tecnico {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name="nombre", nullable = false)
  private String nombre;

  @Column(name="apellido", nullable = false)
  private String apellido;

  @OneToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario;

  @Enumerated(EnumType.STRING)
  @Column(name="tipoDocumento", nullable = false)
  private TipoDocumento tipoDocumento;

  @Column(name="nroDocumento", nullable = false)
  private String nroDocumento;

  @Column(name="cuil", nullable = false)
  private String cuil;

  @Embedded
  private Contacto contacto;

  @Embedded
  private Coordenada coordenada;

  @Column(name="distanciaMaximaEnKMParaSerAvisado")
  private double distanciaMaximaEnKmParaSerAvisado;

  public Tecnico(Contacto contacto) {
    this.contacto = contacto;
  }
}
