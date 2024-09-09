package ar.edu.utn.frba.dds.domain.entities.viandas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
  private LocalDateTime fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float calorias;
  private float pesoEnGramos;
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
