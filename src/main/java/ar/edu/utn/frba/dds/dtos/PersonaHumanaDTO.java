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

  // Campos booleanos para las formas de colaboración
  private boolean donacionDinero;
  private boolean redistribucionViandas;
  private boolean donacionViandas;
  private boolean entregaTarjetas;

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

    // Asignar valores a los campos booleanos basados en `formasContribucionHumanasSet`
    for (FormasContribucionHumanas contribucion : personaHumana.getContribucionesElegidas()) {
      switch (contribucion) {
        case DONACION_DINERO -> this.donacionDinero = true;
        case REDISTRIBUCION_VIANDAS -> this.redistribucionViandas = true;
        case DONACION_VIANDAS -> this.donacionViandas = true;
        case ENTREGA_TARJETAS -> this.entregaTarjetas = true;
      }
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

    // Obtener contribuciones seleccionadas y asignar valores booleanos
    Set<String> contribucionesSeleccionadas = new HashSet<>(context.formParams("formaColaboracion"));
    this.donacionDinero = contribucionesSeleccionadas.contains("DONACION_DINERO");
    this.redistribucionViandas = contribucionesSeleccionadas.contains("REDISTRIBUCION_VIANDAS");
    this.donacionViandas = contribucionesSeleccionadas.contains("DONACION_VIANDAS");
    this.entregaTarjetas = contribucionesSeleccionadas.contains("ENTREGA_TARJETAS");
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof PersonaHumanaDTO)) return false;
    PersonaHumanaDTO that = (PersonaHumanaDTO) obj;
    return Objects.equals(nombre, that.nombre) &&
        Objects.equals(apellido, that.apellido) &&
        Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
        Objects.equals(documentoDTO, that.documentoDTO) &&
        Objects.equals(contactoDTO, that.contactoDTO) &&
        Objects.equals(direccionDTO, that.direccionDTO) &&
        donacionDinero == that.donacionDinero &&
        redistribucionViandas == that.redistribucionViandas &&
        donacionViandas == that.donacionViandas &&
        entregaTarjetas == that.entregaTarjetas;
  }

  @Override
  public int hashCode() {
    return Objects.hash(nombre, apellido, fechaNacimiento, documentoDTO, contactoDTO, direccionDTO,
        donacionDinero, redistribucionViandas, donacionViandas, entregaTarjetas);
  }
}