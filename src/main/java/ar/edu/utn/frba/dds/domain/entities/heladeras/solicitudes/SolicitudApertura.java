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

  public boolean esIngresadaVirtualmente() {
    return !this.isAperturaConcretada() && this.getAccion() == AccionApertura.INGRESAR_VIANDA;
  }

  public boolean esQuitadaVirtualmente() {
    return !this.isAperturaConcretada() && this.getAccion() == AccionApertura.QUITAR_VIANDA;
  }

  public boolean esValida(String codigoTarjeta) {
    return !this.isAperturaConcretada() && Objects.equals(this.codigoTarjeta, codigoTarjeta);
  }
}