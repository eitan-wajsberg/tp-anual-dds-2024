package ar.edu.utn.frba.dds.domain.personasHumanas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class PersonaHumana {
  private Long id;
  @Getter @Setter
  private Usuario usuario;
  @Getter @Setter
  private Documento documento;
  @Getter
  private Set<MedioDeContacto> mediosDeContacto;
  @Getter @Setter
  private Direccion direccion;
  @Getter @Setter
  private String nombre;
  @Getter @Setter
  private String apellido;
  @Getter @Setter
  private LocalDate fechaNacimiento;
  @Getter
  private Set<FormasContribucionHumanas> contribucionesElegidas;
  @Getter
  private Set<Contribucion> contribuciones;

  public PersonaHumana() {
    this.contribucionesElegidas = new HashSet<>();
    this.contribuciones = new HashSet<>();
  }

  public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
    this.mediosDeContacto.add(medioDeContacto);
  }

  public void quitarMedioDeContacto(MedioDeContacto medioDeContacto) {
    try {
      this.mediosDeContacto.remove(medioDeContacto);
    } catch (NoSuchElementException e) {
      System.out.println("Medio de contacto inexistente");
    }
  }

  public void donarDinero(DonacionDinero donacion) {
    // TODO:
  }

  public float calcularPuntajeNeto() {
    // TODO:
    return 0;
  }
}
