package ar.edu.utn.frba.dds.domain.definirPackages;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class PersonaJuridica {

  private String usuario;
  private Set<MedioDeContacto> mediosDeContacto;
  private Direccion direccion;
  private String razonSocial;
  private TipoPersonaJuridica tipo;
  // private Rubro rubro;
  private Set<FormasContribucionJuridicas> contribucionesElegidas;
  private Set<Heladera> heladerasAcargo;
  private Set<DonacionDinero> donaciones;

  public void hacerseCargoDeHeladera(Heladera heladera) {
    //TODO:
  }

  public void darDeBajaHeladera(Heladera heladera) {
    //TODO:
  }

  public void donarDinero(DonacionDinero donacion) {
    //TODO:
  }

  public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
    //TODO:
  }

  public void quitarMedioDeContacto(MedioDeContacto medioDeContacto) {
    //TODO:
  }

}
