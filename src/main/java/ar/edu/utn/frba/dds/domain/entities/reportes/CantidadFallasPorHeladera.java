package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public class CantidadFallasPorHeladera implements Reporte {
  @Override
  public List<String> generarReporte() {
    return null;
  }

  @Override
  public String titulo() {
    return "Cantidad fallas por heladera";
  }
}
