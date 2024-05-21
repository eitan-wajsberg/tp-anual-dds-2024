package ar.edu.utn.frba.dds.domain.definirPackages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respuesta {

  private Pregunta pregunta;
  private PersonaHumana encuestado;
  private String contenido;
}
