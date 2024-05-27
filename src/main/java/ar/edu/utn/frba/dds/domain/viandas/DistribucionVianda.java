package ar.edu.utn.frba.dds.domain.viandas;

import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import java.time.LocalDate;
import java.util.List;

public class DistribucionVianda {

  private Heladera heladeraOrigen;
  private Heladera heladeraDestino;
  private Integer cantidadViandas;
  private String motivo;
  private LocalDate fecha;

  public void distribuir(List<Vianda> viandas) {
    //TODO:
  }
}
