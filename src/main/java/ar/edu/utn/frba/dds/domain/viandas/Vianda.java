package ar.edu.utn.frba.dds.domain.viandas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Vianda implements Contribucion {
  private LocalDateTime fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float calorias;
  private float peso;
  private PersonaHumana colaborador;
  private LocalDateTime fechaDonacion;

  public Vianda(LocalDateTime fechaCaducidad, boolean entregada, String comida, float calorias, float peso, PersonaHumana colaborador, LocalDateTime fechaDonacion) {
    this.fechaCaducidad = fechaCaducidad;
    this.entregada = entregada;
    this.comida = comida;
    this.calorias = calorias;
    this.peso = peso;
    this.colaborador = colaborador;
    this.fechaDonacion = fechaDonacion;
  }
  public void moverA(Heladera heladera) {

  }

  public void donar() {

  }

  public void setEntregada(boolean entregada) { // boolean entregada ?

  }
  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return coeficientes.get("DONACION_VIANDA");
  }

}
