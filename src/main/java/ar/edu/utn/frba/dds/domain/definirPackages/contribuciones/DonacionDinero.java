package ar.edu.utn.frba.dds.domain.definirPackages.contribuciones;

import ar.edu.utn.frba.dds.domain.definirPackages.ReconocimientoTrabajoRealizado;
import java.time.LocalDate;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class DonacionDinero implements Contribucion{
  private float monto;
  private int frecuencia;
  private String unidadFrecuencia;
  private LocalDate fecha;
  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return (monto * coeficientes.get("DONACION_DINERO"));
  }
}
