package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.FormasContribucionHumanas;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import io.javalin.http.Context;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaHumanaDTO implements DTO {
  private Long id;
  private String nombre;
  private String apellido;
  private String fechaNacimiento;
  private ContactoDTO contactoDTO;
  private DocumentoDTO documentoDTO;
  private DireccionDTO direccionDTO;
  private Set<FormasContribucionHumanas> formasContribucionHumanasSet = new HashSet<>();


  public PersonaHumanaDTO(PersonaHumana personaHumana) {
    this.id = personaHumana.getId();
    this.nombre = personaHumana.getNombre();
    this.apellido = personaHumana.getApellido();
    if (personaHumana.getFechaNacimiento() != null) {
      this.fechaNacimiento = personaHumana.getFechaNacimiento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    if (personaHumana.getDireccion() != null) {
      this.direccionDTO = new DireccionDTO(personaHumana.getDireccion());
    }
    if (personaHumana.getDocumento() != null) {
      this.documentoDTO = new DocumentoDTO(personaHumana.getDocumento());
    }
    if (personaHumana.getContacto() != null) {
      this.contactoDTO = new ContactoDTO(personaHumana.getContacto());
    }
  }

  @Override
  public void obtenerFormulario(Context context) {
    this.nombre = context.formParam("nombre");
    this.apellido = context.formParam("apellido");
    this.fechaNacimiento = context.formParam("fechaNacimiento");
    this.direccionDTO = new DireccionDTO();
    this.direccionDTO.obtenerFormulario(context);
    this.contactoDTO = new ContactoDTO();
    this.contactoDTO.obtenerFormulario(context);
    this.documentoDTO = new DocumentoDTO();
    this.documentoDTO.obtenerFormulario(context);
    String[] contribucionesSeleccionadas = context.formParams("formaColaboracion").toArray(new String[0]);
    for (String contribucion : contribucionesSeleccionadas) {
      formasContribucionHumanasSet.add(FormasContribucionHumanas.valueOf(contribucion));
    }
  }

  @Override
  public boolean equals(Object obj) {
    PersonaHumanaDTO that = (PersonaHumanaDTO) obj;
    return Objects.equals(nombre, that.nombre) &&
        Objects.equals(apellido, that.apellido) &&
        Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
        Objects.equals(documentoDTO, that.documentoDTO) &&
        Objects.equals(contactoDTO, that.contactoDTO) &&
        Objects.equals(direccionDTO, that.direccionDTO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombre, apellido, fechaNacimiento, documentoDTO, contactoDTO, direccionDTO);
  }
}
