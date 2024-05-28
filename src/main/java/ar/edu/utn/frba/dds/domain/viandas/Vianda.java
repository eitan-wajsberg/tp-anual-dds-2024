package ar.edu.utn.frba.dds.domain.viandas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Vianda implements Contribucion {
  private LocalDate fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float calorias;
  private float peso;
  private LocalDate fechaDonacion;

  public Vianda(LocalDate fechaCaducidad, boolean entregada, String comida, float calorias, float peso, LocalDate fechaDonacion) {
    this.fechaCaducidad = fechaCaducidad;
    this.entregada = entregada;
    this.comida = comida;
    this.calorias = calorias;
    this.peso = peso;
    this.fechaDonacion = fechaDonacion;
  }

  public Vianda(LocalDate fechaDonacion) {
    this.fechaDonacion = fechaDonacion;
    this.entregada = true;
  }

  public float calcularPuntaje() {
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return coeficientes.get("coeficienteViandasDonadas");
  }

  public boolean estaVencida() {
    return fechaCaducidad.isBefore(LocalDate.now());
  }
}
