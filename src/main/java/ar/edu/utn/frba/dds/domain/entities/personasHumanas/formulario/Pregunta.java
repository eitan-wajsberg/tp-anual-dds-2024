package ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario;

import java.util.ArrayList;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

public interface Pregunta {
  public boolean esValida(String respuesta);
}
