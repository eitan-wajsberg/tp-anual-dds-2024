package ar.edu.utn.frba.dds.domain.definirPackages;

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
