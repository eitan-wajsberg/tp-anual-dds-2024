package ar.edu.utn.frba.dds.dtos;

import io.javalin.http.Context;
import java.util.Objects;
import lombok.Data;

@Data
public class TecnicoDTO implements DTO {
  private String nombre;
  private String apellido;
  private String cuil;
  private Integer radioMaximoParaSerAvisado;
  private String rutaHbs;
  private DireccionDTO direccionDTO;
  private ContactoDTO contactoDTO;
  private DocumentoDTO documentoDTO;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setNombre(context.formParam("nombre"));
    this.setApellido(context.formParam("apellido"));
    this.setCuil(context.formParam("cuil"));
    this.setRadioMaximoParaSerAvisado(Integer.parseInt(Objects.requireNonNull(context.formParam("radio"))));
    this.setRutaHbs(rutaHbs);
    this.direccionDTO = new DireccionDTO();
    this.direccionDTO.obtenerFormulario(context, rutaHbs);
    this.contactoDTO = new ContactoDTO();
    this.contactoDTO.obtenerFormulario(context, rutaHbs);
    this.documentoDTO = new DocumentoDTO();
    this.documentoDTO.obtenerFormulario(context, rutaHbs);
  }
}

