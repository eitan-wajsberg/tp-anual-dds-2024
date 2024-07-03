package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import lombok.Getter;
import lombok.Setter;

public class HorasParaEjecutarAccion {
  private static HorasParaEjecutarAccion instancia;
  @Getter
  @Setter
  private int horasParaEjecutarAccion = 3;

  public static HorasParaEjecutarAccion getInstance() {
    if (instancia == null) {
      instancia = new HorasParaEjecutarAccion();
    }
    return instancia;
  }
}
