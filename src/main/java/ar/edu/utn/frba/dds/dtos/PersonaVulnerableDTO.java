package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonaVulnerableDTO {

  @NotNull(message = "El nombre es obligatorio")
  @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
  private String nombre;

  @NotNull(message = "El apellido es obligatorio")
  @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
  private String apellido;

  @NotNull(message = "La fecha de nacimiento es obligatoria")
  @Past(message = "La fecha de nacimiento debe ser en el pasado")
  private LocalDate fechaDeNacimiento;

  @NotNull(message = "Debe especificar el número de documento")
  @Pattern(regexp = "\\d{7,8}", message = "El número de documento debe tener entre 7 y 8 dígitos")
  private String nroDocumento;

  private String tipoDocumento;

  @Min(value = 0, message = "La cantidad de menores a cargo no puede ser negativa")
  private int menoresAcargo;

  public PersonaVulnerable toPersonaVulnerable() {
    return PersonaVulnerable.builder()
        .nombre(this.nombre)
        .apellido(this.apellido)
        .fechaDeNacimiento(this.fechaDeNacimiento)
        .menoresAcargo(this.menoresAcargo)
        .build();
  }

}