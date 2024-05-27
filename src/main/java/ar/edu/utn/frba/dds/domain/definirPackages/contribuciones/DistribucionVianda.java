package ar.edu.utn.frba.dds.domain.definirPackages.contribuciones;

import ar.edu.utn.frba.dds.domain.definirPackages.ReconocimientoTrabajoRealizado;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DistribucionVianda implements Contribucion{

  private Heladera heladeraOrigen;
  private Heladera heladeraDestino;
  private Integer cantidadViandas;
  private String motivo;
  private LocalDate fecha;

  public void distribuir(List<Vianda> viandas) {
    //TODO:
  }
  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return cantidadViandas * coeficientes.get("DISTRIBUCION_VIANDAS");
  }
}
