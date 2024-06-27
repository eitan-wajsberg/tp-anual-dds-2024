package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public interface Reporte {
  public List<String> generarReporte();

  public String titulo();
}
