package ar.edu.utn.frba.dds.domain.personasJuridicas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.oferta.OfertaCanjeada;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

public class PersonaJuridica {
  @Getter
  private String usuario;
  private Set<MedioDeContacto> mediosDeContacto;
  private Direccion direccion;
  private String razonSocial;
  private TipoPersonaJuridica tipo;
  private Rubro rubro;
  private Set<FormasContribucionJuridicas> contribucionesElegidas;
  @Getter
  private Set<Heladera> heladerasAcargo;
  @Getter
  private Set<Contribucion> contribuciones;
  private Set<OfertaCanjeada> ofertasCanjeadas;
  public PersonaJuridica(){
    this.contribucionesElegidas = new HashSet<>();
    this.contribuciones = new HashSet<>();
    this.heladerasAcargo = new HashSet<>();
    this.ofertasCanjeadas = new HashSet<>();
  }

  public void hacerseCargoDeHeladera(Heladera heladera) {
    this.heladerasAcargo.add(heladera);
    this.contribuciones.add(heladera);
  }

  public void darDeBajaHeladera(Heladera heladera) {
    this.heladerasAcargo.remove(heladera);
    this.contribuciones.remove(heladera);
  }
  public void agregarContribucion(Contribucion contribucion){
    contribuciones.add(contribucion);
  }
  public float puntosGastados(){
    float sum = 0;
    for(OfertaCanjeada ofertaCanjeada: ofertasCanjeadas){
      sum+= ofertaCanjeada.getOferta().getCantidadPuntosNecesarios();
    }
    return sum;
  }
  public float calcularPuntajeNeto(){
    ReconocimientoTrabajoRealizado reconocimientoTrabajoRealizado = ReconocimientoTrabajoRealizado.getInstance();
    return reconocimientoTrabajoRealizado.calcularPuntaje(this.getContribuciones(), this.puntosGastados());
  }

}
