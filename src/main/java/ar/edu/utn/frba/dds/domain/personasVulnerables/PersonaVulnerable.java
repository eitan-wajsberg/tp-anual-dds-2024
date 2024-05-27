package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.time.LocalDate;

public class PersonaVulnerable {

  private String nombre;
  private LocalDate fechaDeNacimiento;
  private LocalDate fechaDeRegistro;
  private String direccion;
  private Integer menoresAcargo;
  private Documento documento;
  private PersonaHumana donanteQueLoRegistro;

}
