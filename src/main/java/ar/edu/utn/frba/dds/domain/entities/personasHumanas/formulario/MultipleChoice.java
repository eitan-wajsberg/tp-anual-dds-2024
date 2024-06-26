package ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MultipleChoice implements Pregunta {
  @Setter
  private String campo;
  @Setter
  private boolean activa;
  private Set<String> opciones;

  public MultipleChoice(String campo, boolean activa) {
    this.campo = campo;
    this.activa = activa;
    this.opciones = new HashSet<>();
  }

  public void agregarOpciones(String ...opciones) {
    Collections.addAll(this.opciones, opciones);
  }

  @Override
  public boolean esValida(String respuesta) {
    return this.opciones.contains(respuesta);
  }
}
