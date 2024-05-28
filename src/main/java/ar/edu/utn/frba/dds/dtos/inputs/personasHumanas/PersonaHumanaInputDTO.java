package ar.edu.utn.frba.dds.dtos.inputs.personasHumanas;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PersonaHumanaInputDTO {
  private Long documentoId;
  private String nombre;
  private String apellido;
  private String mail;
}
