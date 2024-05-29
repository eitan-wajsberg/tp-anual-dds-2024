package ar.edu.utn.frba.dds.domain.personasJuridicas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.oferta.OfertaCanjeada;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PersonaJuridica {
  @Setter
  private String usuario;
  @Setter
  private Contacto contacto;
  @Setter
  private Direccion direccion;
  @Setter
  private String razonSocial;
  @Setter
  private TipoPersonaJuridica tipo;
  @Setter
  private Rubro rubro;
  private Set<FormasContribucionJuridicas> contribucionesElegidas;
  private Set<Heladera> heladerasAcargo;
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
  public void agregarContribucion(Contribucion contribucion) {
    contribuciones.add(contribucion);
  }
  public float puntosGastados() {
    float sum = 0;
    for (OfertaCanjeada ofertaCanjeada: ofertasCanjeadas) {
      sum += ofertaCanjeada.getOferta().getCantidadPuntosNecesarios();
    }
    return sum;
  }
  public float calcularPuntajeNeto() {
    ReconocimientoTrabajoRealizado reconocimientoTrabajoRealizado = ReconocimientoTrabajoRealizado.getInstance();
    return reconocimientoTrabajoRealizado.calcularPuntaje(this.getContribuciones(), this.puntosGastados());
  }
}
