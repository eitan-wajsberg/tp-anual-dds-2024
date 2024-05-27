package ar.edu.utn.frba.dds.domain;

import java.util.Map;
import java.util.Set;

public class ReconocimientoTrabajoRealizado {
  private static ReconocimientoTrabajoRealizado instancia;
  private static Map<String, Float> coeficientes;
  private ReconocimientoTrabajoRealizado(){}
  public static ReconocimientoTrabajoRealizado getInstance(){
    if(instancia == null){
      instancia = new ReconocimientoTrabajoRealizado();
    }
    return instancia;
  }
  public static void cargarCoeficientes(){
    //TODO
  }
  public static Map<String, Float> obtenerCoeficientes(){
    return coeficientes;
  }
  public float calcularPuntaje(Set<Contribucion> contribuciones, float puntajeGastado){
    float puntajeBruto = (float) 0;
    for(Contribucion contribucion : contribuciones){
      puntajeBruto+=contribucion.calcularPuntaje();
    }
    return puntajeBruto - puntajeGastado;
  }
}
