package ar.edu.utn.frba.dds.domain.entities.viandas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DistribucionVianda implements Contribucion {
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

  public DistribucionVianda(LocalDate fecha, int cantidadViandas){
    this.fecha = fecha;
    this.cantidadViandas = cantidadViandas;
  }

  public void distribuir(List<Vianda> viandas) {
    heladeraOrigen.quitarViandas(viandas);
    heladeraDestino.ingresarViandas(viandas);
  }

  public float calcularPuntaje() {
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteViandasDistribuidas");
    return cantidadViandas * coeficiente;
  }
}
