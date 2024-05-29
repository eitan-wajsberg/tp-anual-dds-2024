package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonaVulnerable {
  private String nombre;
  private LocalDate fechaDeNacimiento;
  private LocalDate fechaDeRegistro;
  private String direccion;
  private Integer menoresAcargo;
  private Documento documento;
  private PersonaHumana donanteQueLoRegistro;// hace falta esto?? creeria que no porque la persona tiene las tarjetas que registro
  private Tarjeta tarjeta;

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
