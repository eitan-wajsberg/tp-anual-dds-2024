package ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "respuesta")
public class Respuesta {
  @Id
  @GeneratedValue
  private long id;
  @ManyToOne
  @JoinColumn(name = "pregunta_id", referencedColumnName = "id")
  private Pregunta pregunta;
  @Column(name = "respuestaLibre")
  private String respuestaLibre;
  @ManyToMany
  @JoinTable(name = "opcion_respuesta",
      joinColumns = @JoinColumn(name = "respuesta_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "opcion_id", referencedColumnName = "id"))
  private Set<Opcion> opcionesElegidas;
  public Respuesta(){
    this.opcionesElegidas = new HashSet<>();
  }
  public String getContenido(){return this.respuestaLibre;}
}
