package ar.edu.utn.frba.dds.domain.definirPackages;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
