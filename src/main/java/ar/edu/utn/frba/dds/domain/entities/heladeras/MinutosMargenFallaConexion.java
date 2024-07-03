package ar.edu.utn.frba.dds.domain.entities.heladeras;

import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.HorasParaEjecutarAccion;
import lombok.Getter;
import lombok.Setter;

public class MinutosMargenFallaConexion {
  private static MinutosMargenFallaConexion instancia;
  @Getter
  @Setter
  private int minutosMargenFallaConexion = 7;

  public static MinutosMargenFallaConexion getInstance() {
    if (instancia == null) {
      instancia = new MinutosMargenFallaConexion();
    }
    return instancia;
  }
}
