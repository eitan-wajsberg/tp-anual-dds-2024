package ar.edu.utn.frba.dds.domain.entities.donacionesDinero;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @Column(name = "monto", nullable = false)
  private float monto;

  @Column(name = "frecuencia", nullable = false)
  private int frecuencia;

  @Column(name = "unidadFrecuencia", nullable = false)
  private String unidadFrecuencia;

  @Column(name = "fecha", columnDefinition = "DATE", nullable = false)
  private LocalDate fecha;

  @ManyToOne
  @JoinColumn(name = "id_personaHumana", referencedColumnName = "id")
  private PersonaHumana personaHumana;

  @ManyToOne
  @JoinColumn(name = "id_personaJuridica", referencedColumnName = "id")
  private PersonaJuridica personaJuridica;

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
