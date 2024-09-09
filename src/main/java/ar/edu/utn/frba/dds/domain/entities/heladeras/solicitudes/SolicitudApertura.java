package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import ar.edu.utn.frba.dds.domain.converters.LocalTimeConverter;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Table;
import javax.persistence.Id;

@Getter @Setter
@Entity
@Table(name = "solicitud_apertura")
public class SolicitudApertura {
  @Id @GeneratedValue
  private long id;
  @Convert(converter = LocalTimeConverter.class)
  @Column(name = "fechaSolicitud")
  private LocalDateTime fechaSolicitud;
  @Convert(converter = LocalTimeConverter.class)
  @Column(name = "fechaConcrecion")
  private LocalDateTime fechaConcrecion;
  @ManyToOne
  @Column (name = "tarjeta")
  private Tarjeta tarjeta;
  @Column(name = "apreturaConcretada")
  private boolean aperturaConcretada;
  @Enumerated(EnumType.STRING)
  @Column(name = "accion")
  private AccionApertura accion;
  @Column(name = "cantidadViandas")
  private int cantidadViandas;
  @Transient
  private final int horasParaEjecutarAccion = HorasParaEjecutarAccion.getInstance().getHorasParaEjecutarAccion();

  public SolicitudApertura() {

  }

  public boolean esIngresadaVirtualmente() {
    return !this.isAperturaConcretada()  && this.getAccion() == AccionApertura.INGRESAR_VIANDA
        && LocalDateTime.now().isBefore(fechaSolicitud.plusHours(horasParaEjecutarAccion));
  }

  public boolean esQuitadaVirtualmente() {
    return !this.isAperturaConcretada()  && this.getAccion() == AccionApertura.QUITAR_VIANDA
        && LocalDateTime.now().isBefore(fechaSolicitud.plusHours(horasParaEjecutarAccion));
  }

  public boolean esValida(Tarjeta tarjeta) {
    boolean esValido = !this.isAperturaConcretada() && Objects.equals(this.tarjeta, tarjeta)
        && LocalDateTime.now().isBefore(fechaSolicitud.plusHours(horasParaEjecutarAccion));

    if (esValido) {
      this.aperturaConcretada = true;
    }

    return esValido;
  }
}
