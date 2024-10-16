package ar.edu.utn.frba.dds.domain.entities.viandas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vianda")
public class Vianda implements Contribucion {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "fechaCaducidad", columnDefinition = "DATE")
  private LocalDateTime fechaCaducidad;

  @Column(name = "entregada", columnDefinition = "BIT(1)")
  private boolean entregada;

  @Column(name = "comida", nullable = false)
  private String comida;

  @Column(name = "caloriasEnKcal", columnDefinition = "DECIMAL(5,2)")
  private float caloriasEnKcal;

  @Column(name = "pesoEnGramos", columnDefinition = "DECIMAL(5,2)")
  private float pesoEnGramos;

  @Column(name = "fechaDonacion", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaDonacion;

  @ManyToOne
  @JoinColumn(name = "personaHumana_id", referencedColumnName = "id", nullable = false)
  private PersonaHumana personaHumana;

  public Vianda(LocalDateTime fechaCaducidad, boolean entregada, String comida, float calorias, float pesoEnGramos, LocalDate fechaDonacion) {
    this.fechaCaducidad = fechaCaducidad;
    this.entregada = entregada;
    this.comida = comida;
    this.caloriasEnKcal = calorias;
    this.pesoEnGramos = pesoEnGramos;
    this.fechaDonacion = fechaDonacion;
  }

  public Vianda(LocalDate fechaDonacion) {
    this.fechaDonacion = fechaDonacion;
    this.entregada = true;
  }

  public float calcularPuntaje() {
    return ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteViandasDonadas");
  }

  public TipoContribucion obtenerTipoContribucion() {
    return TipoContribucion.DONACION_VIANDA;
  }

  public LocalDate obtenerFechaRegistro() {
    return this.fechaDonacion;
  }

  public boolean estaVencida() {
    return fechaCaducidad.isBefore(LocalDateTime.now());
  }

  // TODO: This class overrides "equals()" and should therefore also override "hashCode()".
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Vianda)) {
      return false;
    }

    Vianda vianda = (Vianda) o;

    return this.fechaDonacion.equals(vianda.fechaDonacion);
  }
}
