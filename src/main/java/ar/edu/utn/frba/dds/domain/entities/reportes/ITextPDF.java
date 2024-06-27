package ar.edu.utn.frba.dds.domain.entities.reportes;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class ITextPDF {
  private final static String rutaRelativa = "";

  public void generarPDF(String titulo, List<String> parrafos) {
    try {
      String fechaActual = LocalDate.now().toString();
      String ruta = rutaRelativa + titulo + " " + fechaActual + ".pdf";
      Document documento = new Document();
      PdfWriter.getInstance(documento, new FileOutputStream(ruta));
      documento.open();
      documento.addTitle(titulo);
      documento.add(new Paragraph(titulo, new Font(Font.FontFamily.TIMES_ROMAN, 25, Font.BOLDITALIC)));
      documento.add(new Paragraph(fechaActual, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC)));
      documento.add(Chunk.NEWLINE);
      for (String parrafo : parrafos) {
        documento.add(new Paragraph(parrafo, new Font(Font.FontFamily.TIMES_ROMAN, 12)));
      }
      documento.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
