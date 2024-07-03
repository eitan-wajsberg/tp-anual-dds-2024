package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.reportes.AdapterPDFiText;
import ar.edu.utn.frba.dds.domain.entities.reportes.CantidadFallasPorHeladera;
import ar.edu.utn.frba.dds.domain.entities.reportes.CantidadViandasPorColaborador;
import ar.edu.utn.frba.dds.domain.entities.reportes.GeneradorReportes;
import ar.edu.utn.frba.dds.domain.entities.reportes.ITextPDF;
import ar.edu.utn.frba.dds.domain.entities.reportes.MovimientoViandasPorHeladera;
import ar.edu.utn.frba.dds.domain.entities.reportes.Temporalidad;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;

public class GeneradorReportesMain
{
  public static void main(String[] args) {
    // configuro generador de reportes
    String rutaReportes = "src/resources/reportes";
    AdapterPDFiText adaptador = new AdapterPDFiText(new ITextPDF(rutaReportes));
    GeneradorReportes.getInstance().setAdapterPDF(adaptador);
    GeneradorReportes.getInstance().setTemporalidad(Temporalidad.SEMANAL);

    // configuro reportes
    CantidadFallasPorHeladera fallasHeladeras = new CantidadFallasPorHeladera();
    fallasHeladeras.setRepositorioHeladera(new RepositorioHeladera());

    CantidadViandasPorColaborador viandasPorColaborador = new CantidadViandasPorColaborador();
    viandasPorColaborador.setRepositorioColaboradores(new RepositorioPersonaHumana());

    MovimientoViandasPorHeladera movimientosHeladera = new MovimientoViandasPorHeladera();
    movimientosHeladera.setRepositorioHeladera(new RepositorioHeladera());

    GeneradorReportes.getInstance().agregarReportes(fallasHeladeras, viandasPorColaborador, movimientosHeladera);

    // genero reportes
    GeneradorReportes.getInstance().generarReportes();
  }
}
