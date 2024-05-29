package ar.edu.utn.frba.dds.domain.personasHumanas;

import java.util.ArrayList;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public interface Pregunta {
  public boolean esValida(String respuesta);
}
