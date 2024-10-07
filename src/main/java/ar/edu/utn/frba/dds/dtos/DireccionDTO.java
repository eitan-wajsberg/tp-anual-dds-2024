package ar.edu.utn.frba.dds.dtos;

import io.javalin.http.Context;
import lombok.Data;

@Data
public class DireccionDTO {
  private String calle;
  private String altura;
  private String provincia;
  private String municipio;
  private String rutaHbs;
  private boolean obligatoria;

  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setCalle(context.formParam("calle"));
    this.setAltura(context.formParam("altura"));
    this.setProvincia(context.formParam("provincia"));
    this.setMunicipio(context.formParam("municipio"));
    this.setRutaHbs(rutaHbs);
  }
}
