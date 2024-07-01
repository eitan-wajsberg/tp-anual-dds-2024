package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class GeneradorReportes {
  @Getter @Setter
  private List<Reporte> reportes;
  @Getter @Setter
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
    return switch (temporalidad) {
      case DIARIO -> ahora.minusDays(1);
      case SEMANAL -> ahora.minusWeeks(1);
      case MENSUAL -> ahora.minusMonths(1);
      case ANUAL -> ahora.minusYears(1);
      default ->
          ahora; // En caso de que no se establezca ninguna temporalidad, usar la fecha actual.
    };
  }

  public void generarReportes() {
    LocalDate fechaInicio = calcularFechaInicio(temporalidad);
    LocalDate fechaFin = LocalDate.now();

    for (Reporte reporte : reportes) {
      List<String> parrafos = reporte.generarReporte(fechaInicio, fechaFin);
      adapterPDF.exportarPDF(reporte.titulo(), parrafos);
    }
  }
}
