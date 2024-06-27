package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public interface IAdapterPDF {
  public void exportarPDF(String titulo, List<String> parrafos);
}
