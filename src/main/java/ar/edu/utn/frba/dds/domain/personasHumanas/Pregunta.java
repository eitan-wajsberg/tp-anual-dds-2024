package ar.edu.utn.frba.dds.domain.personasHumanas;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pregunta {

  private String campo;
  private boolean activa;
  private Set<String> opciones;
  private String tipo;

}
