package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public class MovimientoViandasPorHeladera implements Reporte {
  @Override
  public List<String> generarReporte() {
    return null;
  }

  @Override
  public String titulo() {
    return "Movimiento viandas por heladera";
  }
}
