package ar.edu.utn.frba.dds.domain.entities.donacionesDinero;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DonacionDinero implements Contribucion {
  private float monto;
  private int frecuencia;
  private String unidadFrecuencia;
  private LocalDate fecha;

  public float calcularPuntaje(){
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficientePesosDonados");
    return monto * coeficiente;
  }

  public TipoContribucion obtenerTipoContribucion() {
    return TipoContribucion.DONACION_DINERO;
  }

  public LocalDate obtenerFechaRegistro() {
    return this.fecha;
  }
}
