package ar.edu.utn.frba.dds.domain.personasHumanas.formulario;

import ar.edu.utn.frba.dds.domain.personasHumanas.formulario.Pregunta;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PreguntaAbierta implements Pregunta {
  private String campo;
  private boolean activa;

  @Override
  public boolean esValida(String respuesta) {
    return true;
  }
}
