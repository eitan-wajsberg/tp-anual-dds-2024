package ar.edu.utn.frba.dds.domain.entities.viandas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="vianda")
@NoArgsConstructor
public class Vianda implements Contribucion {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name="fechaCaducidad", columnDefinition = "DATE")
  private LocalDateTime fechaCaducidad;

  @Column(name="entregada", columnDefinition = "BIT(1)")
  private boolean entregada;

  @Column(name="comida")
  private String comida;

  @Column(name="calorias") // TODO: en qué (k?) y cuántos nros dsp de la coma?
  private float calorias;

  @Column(name="pesoEnGramos", columnDefinition = "DECIMAL(5,2)")
  private float pesoEnGramos;

  @Column(name="fechaDonacion", columnDefinition = "DATE")
  private LocalDate fechaDonacion;

  public Vianda(LocalDateTime fechaCaducidad, boolean entregada, String comida, float calorias, float pesoEnGramos, LocalDate fechaDonacion) {
    this.fechaCaducidad = fechaCaducidad;
    this.entregada = entregada;
    this.comida = comida;
    this.calorias = calorias;
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

  @Override
  public boolean equals(Object o){
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
