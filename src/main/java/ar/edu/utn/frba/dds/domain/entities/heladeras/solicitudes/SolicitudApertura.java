package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import ar.edu.utn.frba.dds.domain.converters.LocalDateTimeAttributeConverter;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Table;
import javax.persistence.Id;

@Getter @Setter
@Entity
@Table(name = "solicitud_apertura")
@NoArgsConstructor
public class SolicitudApertura {
  @Id @GeneratedValue
  private long id;

  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @Column(name = "fechaSolicitud", nullable = false)
  private LocalDateTime fechaSolicitud;

  @Getter
  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @Column(name = "fechaConcrecion")
  private LocalDateTime fechaConcrecion;

  @ManyToOne
  @JoinColumn(name="tarjeta_id", referencedColumnName = "id", nullable = false)
  private Tarjeta tarjeta;

  @Column(name = "apreturaConcretada")
  private boolean aperturaConcretada;

  @Enumerated(EnumType.STRING)
  @Column(name = "accion", nullable = false)
  private AccionApertura accion;

  @Column(name = "cantidadViandas", nullable = false)
  private int cantidadViandas;

  @Transient
  private final int horasParaEjecutarAccion = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("horas_para_ejecutar_accion"));

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
  public void setCodigoTarjeta(String codigo){
    this.tarjeta.setCodigo(codigo);
  }
}
