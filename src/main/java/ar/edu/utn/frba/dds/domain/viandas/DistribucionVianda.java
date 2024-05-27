package ar.edu.utn.frba.dds.domain.viandas;

import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DistribucionVianda {
  private Heladera heladeraOrigen;
  private Heladera heladeraDestino;
  private int cantidadViandas;
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
