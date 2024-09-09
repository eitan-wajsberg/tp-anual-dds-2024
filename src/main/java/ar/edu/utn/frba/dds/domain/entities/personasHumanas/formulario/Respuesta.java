package ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  @Transient
  private Pregunta pregunta;
  private String contenido;
}
