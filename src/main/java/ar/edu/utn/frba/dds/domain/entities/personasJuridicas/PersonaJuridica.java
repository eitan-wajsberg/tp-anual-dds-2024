package ar.edu.utn.frba.dds.domain.entities.personasJuridicas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity @Table(name="persona_juridica")
public class PersonaJuridica {

  @Id @GeneratedValue
  private Long id;

  @Setter
  @OneToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario;                                      //TODO: Está bien?

  @Setter
  @OneToOne
  @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private Contacto contacto;

  @Setter
  @Embedded
  private Direccion direccion;

  @Setter
  @Column(name = "razonSocial")
  private String razonSocial;

  @Setter
  @Enumerated(EnumType.STRING)
  private TipoPersonaJuridica tipo;

  @Setter
  @OneToOne
  @JoinColumn(name = "rubro_id", referencedColumnName = "id")
  private Rubro rubro;

  //TODO:
  private Set<FormasContribucionJuridicas> contribucionesElegidas;

  //TODO:
  private Set<Heladera> heladerasAcargo;

  //TODO:
  private Set<Contribucion> contribuciones;

  //TODO:
  private Set<Oferta> ofertas;

  public PersonaJuridica(){
    this.contribucionesElegidas = new HashSet<>();
    this.contribuciones = new HashSet<>();
    this.heladerasAcargo = new HashSet<>();
  }

  public void hacerseCargoDeHeladera(Heladera heladera) {
    this.heladerasAcargo.add(heladera);
    this.contribuciones.add(heladera);
  }

  public void darDeBajaHeladera(Heladera heladera) {
    this.heladerasAcargo.remove(heladera);
    this.contribuciones.remove(heladera);
  }

  public void agregarContribucion(Contribucion contribucion) {
    contribuciones.add(contribucion);
  }
}
