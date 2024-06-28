package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.time.LocalDate;
import java.util.List;

public class CantidadFallasPorHeladera implements Reporte {

  public List<String> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
    return null;
  }

  public String titulo() {
    return "Cantidad fallas por heladera";
  }
}
