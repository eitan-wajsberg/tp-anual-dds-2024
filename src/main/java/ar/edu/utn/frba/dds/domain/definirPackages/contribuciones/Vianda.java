package ar.edu.utn.frba.dds.domain.definirPackages.contribuciones;

import ar.edu.utn.frba.dds.domain.definirPackages.PersonaHumana;
import ar.edu.utn.frba.dds.domain.definirPackages.ReconocimientoTrabajoRealizado;
import java.time.LocalDateTime;
import java.util.Map;

public class Vianda implements Contribucion{

  private Heladera heladera;
  private LocalDateTime fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float calorias;
  private float peso;
  private PersonaHumana colaborador;
  private LocalDateTime fechaDonacion;

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
