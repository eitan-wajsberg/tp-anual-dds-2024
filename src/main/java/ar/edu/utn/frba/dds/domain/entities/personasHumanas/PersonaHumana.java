package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.IObserverNotificacion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.GestorSuscripciones;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.TipoSuscripcion;
import ar.edu.utn.frba.dds.domain.entities.oferta.OfertaCanjeada;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario.Respuesta;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.formulario.RespuestaNoValidaException;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.UsoDeTarjeta;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;

import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.mail.MessagingException;

@Entity
@Table(name = "persona_humana")
public class PersonaHumana extends IObserverNotificacion {

  @Id @GeneratedValue
  @Getter @Setter
  private Long id;

  @Getter @Setter
  @OneToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario; //TODO: Está bien

  @Getter @Setter
  @Enumerated(EnumType.STRING)
  @Column(name="tipoDocumento")
  private TipoDocumento tipoDocumento;

  @Getter @Setter
  @Column(name="nroDocumento")
  private String nroDocumento;

  @Getter @Setter
  @OneToOne
  @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private Contacto contacto;

  @Getter @Setter
  @Embedded
  private Direccion direccion;

  @Getter @Setter
  @Column(name="nombre")
  private String nombre;

  @Getter @Setter
  @Column(name="apellido")
  private String apellido;

  @Getter @Setter
  @Column(name = "fechaNacimiento", columnDefinition = "DATE")
  private LocalDate fechaNacimiento;

  @Getter //TODO:
  private Set<FormasContribucionHumanas> contribucionesElegidas;

  @Getter //TODO:
  private Set<Contribucion> contribuciones;

  @Getter //TODO:
  private Set<OfertaCanjeada> ofertasCanjeadas;

  @Getter
  @OneToMany
  @JoinColumn(name = "personaHumana_id", referencedColumnName = "id")
  private List<Tarjeta> tarjetasSinEntregar;

  @Getter //TODO:
  private List<Respuesta> formulario;

  @Getter
  @OneToMany
  @JoinColumn(name = "personaHumana_id", referencedColumnName = "id")
  private List<Tarjeta> tarjetasColaboracion;

  @Getter
  @OneToOne
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
  private Tarjeta tarjetaEnUso;

  public PersonaHumana() {
    this.contribucionesElegidas = new HashSet<>();
    this.contribuciones = new HashSet<>();
    this.ofertasCanjeadas = new HashSet<>();
    this.tarjetasSinEntregar = new ArrayList<>();
    this.formulario = new ArrayList<>();
  }

  public float puntosGastados() {
    float sum = 0;
    for (OfertaCanjeada ofertaCanjeada : ofertasCanjeadas) {
      sum += ofertaCanjeada.getOferta().getCantidadPuntosNecesarios();
    }
    return sum;
  }

  public float calcularPuntajeNeto() {
    ReconocimientoTrabajoRealizado reconocimientoTrabajoRealizado = ReconocimientoTrabajoRealizado.getInstance();
    return reconocimientoTrabajoRealizado.calcularPuntaje(this.getContribuciones(), this.puntosGastados());
  }

  public void agregarContribucion(Contribucion contribucion) {
    contribuciones.add(contribucion);
  }

  public void agregarOfertaCanjeada(OfertaCanjeada ofertaCanjeada) {
    ofertasCanjeadas.add(ofertaCanjeada);
  }

  public void agregarTarjetaSinEntregar(Tarjeta tarjeta) {
    if (this.direccion == null) {
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

  public void serNotificadoPor(Mensaje mensaje) {
    try {
      contacto.enviarMensaje(mensaje);
    } catch (MessagingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public void asignarTarjetaParaColaborar(Tarjeta tarjeta) {
    if (this.direccion == null) {
      throw new DireccionIncompletaException();
    }

    darTarjetaDeBaja();
    this.tarjetasColaboracion.add(tarjeta);
    this.tarjetaEnUso = tarjeta;
  }

  public void darTarjetaDeBaja(){
    if (this.tarjetaEnUso != null) {
      this.tarjetaEnUso.setFechaBaja(LocalDate.now());
    }
    this.tarjetaEnUso = null;
  }

  public void usarTarjeta(Heladera heladera){
    if (!heladera.validarApertura(this.tarjetaEnUso)) {
      throw new NoHaySolicitudActivaException();
    }

    this.tarjetaEnUso.agregarUso(new UsoDeTarjeta(LocalDateTime.now(), heladera));
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof PersonaHumana persona)) {
      return false;
    }

    // comparar distribuciones
    boolean mismasContribuciones = this.contribuciones.size() == persona.contribuciones.size();
    if (mismasContribuciones) {
      mismasContribuciones =
          this.contribuciones.stream().allMatch(contribucion1 ->
            persona.contribuciones.stream().anyMatch(contribucion2 ->
                contribucion2.equals(contribucion1)));
    }

    return
        mismasContribuciones
        && this.nombre.equals(persona.nombre)
        && this.apellido.equals(persona.apellido)
        && this.nroDocumento.equals(persona.nroDocumento)
        && this.contacto.equals(persona.contacto);
  }
}
