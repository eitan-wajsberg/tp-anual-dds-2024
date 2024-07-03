package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import lombok.Getter;

public class HorasParaEjecutarAccion {
  private static HorasParaEjecutarAccion instancia;
  @Getter
  private int horasParaEjecutarAccion = 3;

  public static HorasParaEjecutarAccion getInstance() {
    if (instancia == null) {
      instancia = new HorasParaEjecutarAccion();
    }
    return instancia;
  }
}
