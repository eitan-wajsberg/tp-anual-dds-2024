package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.documento.Documento;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import io.javalin.http.Context;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaVulnerableDTO implements DTO {
  private String nombre;
  private String apellido;
  private String fechaDeNacimiento;
  private String menoresAcargo;
  private DocumentoDTO documentoDTO;
  private DireccionDTO direccionDTO;
  private Long id;

  public PersonaVulnerableDTO(PersonaVulnerable vulnerable) {
    this.setNombre(vulnerable.getNombre());
    this.setApellido(vulnerable.getApellido());
    this.setMenoresAcargo(vulnerable.getMenoresAcargo().toString());
    this.setFechaDeNacimiento(vulnerable.getFechaDeNacimiento().toString());
    this.setDireccionDTO(new DireccionDTO(vulnerable.getDireccion()));
    this.setDocumentoDTO(new DocumentoDTO(vulnerable.getDocumento()));
    this.setId(vulnerable.getId());
  }

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    PersonaVulnerableDTO that = (PersonaVulnerableDTO) obj;

    return Objects.equals(this.nombre, that.nombre) &&
        Objects.equals(this.apellido, that.apellido) &&
        Objects.equals(this.documentoDTO, that.documentoDTO) &&
        Objects.equals(this.fechaDeNacimiento, that.fechaDeNacimiento) &&
        Objects.equals(this.direccionDTO, that.direccionDTO) &&
        Objects.equals(this.menoresAcargo, that.menoresAcargo);
  }
}

