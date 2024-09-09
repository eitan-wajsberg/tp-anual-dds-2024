package ar.edu.utn.frba.dds.domain.entities.donacionesDinero;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name="donacion_dinero")
public class DonacionDinero implements Contribucion {

  @Id  @GeneratedValue
  private Long id;

  @Column(name = "monto")
  private float monto;

  @Column(name = "frecuencia")
  private int frecuencia;

  @Column(name = "unidadFrecuencia")
  private String unidadFrecuencia;

  @Column(name = "fecha", columnDefinition = "DATE")
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
