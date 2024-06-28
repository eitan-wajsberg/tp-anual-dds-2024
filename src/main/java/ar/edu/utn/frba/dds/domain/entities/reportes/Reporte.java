package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.time.LocalDate;
import java.util.List;

public interface Reporte {
  public List<String> generarReporte(LocalDate fechaInicio, LocalDate fechaFin);

  public String titulo();
}
