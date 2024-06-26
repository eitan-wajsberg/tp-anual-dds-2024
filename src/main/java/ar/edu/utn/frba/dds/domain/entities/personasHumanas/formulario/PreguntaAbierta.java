package ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario;

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
