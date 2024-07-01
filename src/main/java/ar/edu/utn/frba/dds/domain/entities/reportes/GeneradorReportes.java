package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class GeneradorReportes {
  private List<Reporte> reportes;
  private IAdapterPDF adapterPDF;
  private static GeneradorReportes instancia = null;

  public enum Temporalidad {
    DIARIO,
    SEMANAL,
    MENSUAL,
    ANUAL
  }

  @Getter @Setter
  private Temporalidad temporalidad;

  public static GeneradorReportes getInstance() {
    if (instancia == null) {
      instancia = new GeneradorReportes();
    }
    return instancia;
  }

  private LocalDate calcularFechaInicio(Temporalidad temporalidad) {
    LocalDate ahora = LocalDate.now();
    switch (temporalidad) {
      case DIARIO:
        return ahora.minusDays(1);
      case SEMANAL:
        return ahora.minusWeeks(1);
      case MENSUAL:
        return ahora.minusMonths(1);
      case ANUAL:
        return ahora.minusYears(1);
      default:
        return ahora; // En caso de que no se establezca ninguna temporalidad, usar la fecha actual.
    }
  }

  public void generarReportes() {
    LocalDate fechaInicio = calcularFechaInicio(temporalidad);
    LocalDate fechaFin = LocalDate.now();
    for (Reporte reporte : reportes) {
      adapterPDF.exportarPDF(reporte.titulo(), reporte.generarReporte(fechaInicio, fechaFin));
    }
  }
}
