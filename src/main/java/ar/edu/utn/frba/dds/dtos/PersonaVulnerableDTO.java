package ar.edu.utn.frba.dds.dtos;

import io.javalin.http.Context;
import lombok.Data;

@Data
public class PersonaVulnerableDTO implements DTO {
  private String nombre;
  private String apellido;
  private String fechaDeNacimiento;
  private String menoresAcargo;
  private DocumentoDTO documentoDTO;
  private DireccionDTO direccionDTO;

  @Override
  public void obtenerFormulario(Context context) {
    this.setNombre(context.formParam("nombre"));
    this.setApellido(context.formParam("apellido"));
    this.setFechaDeNacimiento(context.formParam("fecha"));
    this.setMenoresAcargo(context.formParam("cantidadMenores"));
    this.documentoDTO = new DocumentoDTO();
    this.documentoDTO.obtenerFormulario(context);
    this.direccionDTO = new DireccionDTO();
    this.direccionDTO.obtenerFormulario(context);
  }
}

