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

  public DistribucionVianda(Heladera heladeraOrigen, Heladera heladeraDestino, int cantidadViandas, String motivo, LocalDate fecha) {
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
    this.cantidadViandas = cantidadViandas;
    this.motivo = motivo;
    this.fecha = fecha;
  }

  public void distribuir(List<Vianda> viandas) {
    heladeraOrigen.quitarViandas(viandas);
    heladeraDestino.ingresarViandas(viandas);
  }

  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return cantidadViandas * coeficientes.get("coeficienteViandasDistribuidas");
  }
}
