package ar.edu.utn.frba.dds.dtos;

import io.javalin.http.Context;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaJuridicaDTO implements DTO {
  private String razonSocial;
  private String tipo;
  private ContactoDTO contactoDTO;
  private DireccionDTO direccionDTO;

  @Override
  public void obtenerFormulario(Context context) {
    this.razonSocial = context.formParam("razonSocial");
    this.tipo = context.formParam("tipo");
    this.contactoDTO = new ContactoDTO();
    this.contactoDTO.obtenerFormulario(context);
    this.direccionDTO = new DireccionDTO();
    this.direccionDTO.obtenerFormulario(context);
  }
}
