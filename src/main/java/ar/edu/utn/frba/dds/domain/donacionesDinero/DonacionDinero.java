package ar.edu.utn.frba.dds.domain.donacionesDinero;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter @Setter
public class DonacionDinero implements Contribucion {
  private float monto;
  private int frecuencia;
  private String unidadFrecuencia;
  private LocalDate fecha;

  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return (monto * coeficientes.get("coeficientePesosDonados"));
  }
}
