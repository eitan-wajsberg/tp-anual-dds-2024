package ar.edu.utn.frba.dds.domain.entities.reportes;

import java.util.List;

public class AdapterPDFiText implements IAdapterPDF {
  private ITextPDF iText;

  public AdapterPDFiText(ITextPDF iText) {
    this.iText = iText;
  }

  @Override
  public void exportarPDF(String titulo, List<String> parrafos) {
    iText.generarPDF(titulo, parrafos);
  }
}
