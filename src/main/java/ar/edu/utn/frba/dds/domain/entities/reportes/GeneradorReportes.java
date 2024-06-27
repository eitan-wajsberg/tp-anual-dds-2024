package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public class GeneradorReportes {
  private List<Reporte> reportes;
  private IAdapterPDF adapterPDF;
  private static GeneradorReportes instancia = null;

  public static GeneradorReportes getInstance() {
    if (instancia == null) {
      instancia = new GeneradorReportes();
    }
    return instancia;
  }

  public void generarReportes() {
    for (Reporte reporte : reportes) {
      adapterPDF.exportarPDF(reporte.titulo(), reporte.generarReporte());
    }
  }
}
