package ar.edu.utn.frba.dds.dtos.outputs.personasHumanas;

import lombok.Data;

@Data
public class PersonaHumanaOutputDTO {
  private Long id;
  private Long documentoId;
  private String nombre;
  private String apellido;
  private String mail;
}
