package ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respuesta {
  private Pregunta pregunta;
  private String contenido;
}
