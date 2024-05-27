package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class PersonaVulnerable {
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private LocalDate fechaDeNacimiento;
  @Getter @Setter
  private LocalDate fechaDeRegistro;
  @Getter @Setter
  private String direccion;
  @Getter @Setter
  private Integer menoresAcargo;
  @Getter @Setter
  private Documento documento;
  @Getter @Setter
  private PersonaHumana donanteQueLoRegistro; // hace falta esto?? creeria que no porque la persona tiene las tarjetas que registro

  public PersonaVulnerable(String nombre, LocalDate fechaDeNacimiento, LocalDate fechaDeRegistro, String direccion, Integer menoresAcargo, Documento documento, PersonaHumana donanteQueLoRegistro) {
    this.nombre = nombre;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.fechaDeRegistro = fechaDeRegistro;
    this.direccion = direccion;
    this.menoresAcargo = menoresAcargo;
    this.documento = documento;
    this.donanteQueLoRegistro = donanteQueLoRegistro;
  }
}
