package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SolicitudApertura {
  private LocalDateTime fecha;
  private String codigoTarjeta;
  private boolean aperturaConcretada;
  private AccionApertura accion;
  private int cantidadViandas;
  private final int horasParaEjecutarAccion = HorasParaEjecutarAccion.getInstance().getHorasParaEjecutarAccion();

  public SolicitudApertura() {

  }

  public boolean esIngresadaVirtualmente() {
    return !this.isAperturaConcretada()  && this.getAccion() == AccionApertura.INGRESAR_VIANDA
        && LocalDateTime.now().isBefore(fecha.plusHours(horasParaEjecutarAccion));
  }

  public boolean esQuitadaVirtualmente() {
    return !this.isAperturaConcretada()  && this.getAccion() == AccionApertura.QUITAR_VIANDA
        && LocalDateTime.now().isBefore(fecha.plusHours(horasParaEjecutarAccion));
  }

  public boolean esValida(String codigoTarjeta) {
    boolean esValido = !this.isAperturaConcretada() && Objects.equals(this.codigoTarjeta, codigoTarjeta)
        && LocalDateTime.now().isBefore(fecha.plusHours(horasParaEjecutarAccion));

    if (esValido) {
      this.aperturaConcretada = true;
    }

    return esValido;
  }
}
