package ar.edu.utn.frba.dds.dtos;

import io.javalin.http.Context;
import lombok.Data;

@Data
public class DocumentoDTO implements DTO {
  private String tipoDocumento;
  private String nroDocumento;

  @Override
  public void obtenerFormulario(Context context) {
    this.setNroDocumento(context.formParam("nroDocumento"));
    this.setTipoDocumento(context.formParam("tipoDocumento"));
  }
}
