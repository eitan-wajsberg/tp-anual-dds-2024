package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

public class GeneradorReportes {
  private List<Reporte> reportes;
  private IAdapterPDF adapterPDF;
  private static GeneradorReportes instancia = null;
  private String temporalidad;

  public static GeneradorReportes getInstance() {
    if (instancia == null) {
      instancia = new GeneradorReportes();
    }
    return instancia;
  }

  public void generarReportes() {
    for (Reporte reporte : reportes) {
      // TODO: Parametrizar la temporalidad
      adapterPDF.exportarPDF(reporte.titulo(), reporte.generarReporte(LocalDate.now().minusWeeks(1), LocalDate.now()));
    }
  }
}
