package ar.edu.utn.frba.dds.domain.personasHumanas;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PreguntaAbierta implements Pregunta {
  private String campo;
  private boolean activa;
  private String tipo;

  @Override
  public boolean esValida(String respuesta) {
    return true;
  }
}
