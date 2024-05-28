package ar.edu.utn.frba.dds.domain.personasHumanas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.oferta.OfertaCanjeada;
import ar.edu.utn.frba.dds.domain.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

public class PersonaHumana {
  @Getter @Setter
  private Long id;
  @Getter @Setter
  private Usuario usuario;
  @Getter @Setter
  private Documento documento;
  @Getter @Setter
  private Contacto contacto;
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
  @Getter
  private Set<OfertaCanjeada> ofertasCanjeadas;

  public PersonaHumana() {
    this.contribucionesElegidas = new HashSet<>();
    this.contribuciones = new HashSet<>();
    this.ofertasCanjeadas = new HashSet<>();
  }
  public float puntosGastados(){
    float sum = 0;
    for(OfertaCanjeada ofertaCanjeada: ofertasCanjeadas){
      sum+= ofertaCanjeada.getOferta().getCantidadPuntosNecesarios();
    }
    return sum;
  }
  public float calcularPuntajeNeto() {
    ReconocimientoTrabajoRealizado reconocimientoTrabajoRealizado = ReconocimientoTrabajoRealizado.getInstance();
    return reconocimientoTrabajoRealizado.calcularPuntaje(this.getContribuciones(), this.puntosGastados());
  }

  public void agregarContribucion(Contribucion contribucion){
    contribuciones.add(contribucion);
  }
  public void agregarOfertaCanjeada(OfertaCanjeada ofertaCanjeada){ofertasCanjeadas.add(ofertaCanjeada);}
  
}
