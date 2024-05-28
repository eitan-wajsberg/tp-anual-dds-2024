package ar.edu.utn.frba.dds.domain.personasHumanas;

import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaHumana {
  private Long id;
  private String usuario;
  private Documento documento;
  private Set<MedioDeContacto> mediosDeContacto;
  private Direccion direccion;
  private String nombre;
  private String apellido;
  private LocalDate fechaNacimiento;
  private Set<FormasContribucionHumanas> contribucionesElegidas;
  private Set<DonacionDinero> donaciones;

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
