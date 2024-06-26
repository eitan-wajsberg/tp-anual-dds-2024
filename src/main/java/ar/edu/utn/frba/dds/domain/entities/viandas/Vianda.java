package ar.edu.utn.frba.dds.domain.entities.viandas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Vianda implements Contribucion {
  private LocalDate fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float calorias;
  private float pesoEnGramos;
  private LocalDate fechaDonacion;

  public Vianda(LocalDate fechaCaducidad, boolean entregada, String comida, float calorias, float pesoEnGramos, LocalDate fechaDonacion) {
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
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteViandasDonadas");
    return coeficiente;
  }

  public boolean estaVencida() {
    return fechaCaducidad.isBefore(LocalDate.now());
  }
}
