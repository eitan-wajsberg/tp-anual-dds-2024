package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public class CantidadViandasPorColaborador implements Reporte {
  @Override
  public List<String> generarReporte() {
    return null;
  }

  @Override
  public String titulo() {
    return "Cantidad viandas por colaborador";
  }
}
