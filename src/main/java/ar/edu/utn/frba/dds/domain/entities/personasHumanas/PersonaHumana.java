package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.oferta.OfertaCanjeada;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario.Respuesta;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario.RespuestaNoValidaException;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
  @Getter
  private List<Tarjeta> tarjetasSinEntregar;
  @Getter
  private List<Respuesta> formulario;

  public PersonaHumana() {
    this.contribucionesElegidas = new HashSet<>();
    this.contribuciones = new HashSet<>();
    this.ofertasCanjeadas = new HashSet<>();
    this.tarjetasSinEntregar = new ArrayList<>();
    this.formulario = new ArrayList<>();
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
  public void agregarOfertaCanjeada(OfertaCanjeada ofertaCanjeada){
    ofertasCanjeadas.add(ofertaCanjeada);
  }

  public void agregarTarjetaSinEntregar(Tarjeta tarjeta){
    if(this.direccion == null) {
      throw new DireccionIncompletaException();
    }
    this.tarjetasSinEntregar.add(tarjeta);
  }

  public void agregarRespuestaAlFormulario(Respuesta respuesta) {
    if (!respuesta.getPregunta().esValida(respuesta.getContenido())) {
      throw new RespuestaNoValidaException();
    }
    this.formulario.add(respuesta);
  }

  public void agregarFormaDeContribucion(FormasContribucionHumanas forma) {
    this.contribucionesElegidas.add(forma);
  }

  public void quitarFormaDeContribucion(FormasContribucionHumanas forma) {
    this.contribucionesElegidas.remove(forma);
  }
}
