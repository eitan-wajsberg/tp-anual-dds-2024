package ar.edu.utn.frba.dds.domain.entities.personasJuridicas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  private Usuario usuario;

  @Setter
  private Contacto contacto;

  @Setter
  private Direccion direccion;

  @Setter
  private String razonSocial;

  @Setter
  private TipoPersonaJuridica tipo;

  @Setter
  private Rubro rubro;

  private Set<FormasContribucionJuridicas> contribucionesElegidas;

  private Set<Heladera> heladerasAcargo;

  private Set<Contribucion> contribuciones;

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
