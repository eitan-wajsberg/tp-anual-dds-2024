package ar.edu.utn.frba.dds.domain.personasHumanas.formulario;

import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.personasHumanas.formulario.Pregunta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respuesta {
  private Pregunta pregunta;
  private String contenido;
}
