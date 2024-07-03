package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.HorasParaEjecutarAccion;
import lombok.Getter;
import lombok.Setter;

public class UmbralDistanciaEnKm {
  private static UmbralDistanciaEnKm instancia;
  @Getter
  @Setter
  private int umbralDistanciaEnKm = 10;

  public static UmbralDistanciaEnKm getInstance() {
    if (instancia == null) {
      instancia = new UmbralDistanciaEnKm();
    }
    return instancia;
  }
}
