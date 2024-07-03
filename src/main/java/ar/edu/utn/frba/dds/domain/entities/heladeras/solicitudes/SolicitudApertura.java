package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Objects;
import java.util.Properties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SolicitudApertura {
  private LocalDateTime fecha;
  private String codigoTarjeta;
  private boolean aperturaConcretada;
  private AccionApertura accion;
  private int cantidadViandas;

  public SolicitudApertura() {

  }

  public boolean esIngresadaVirtualmente() {
    return !this.isAperturaConcretada() && this.getAccion() == AccionApertura.INGRESAR_VIANDA;
  }

  public boolean esQuitadaVirtualmente() {
    return !this.isAperturaConcretada() && this.getAccion() == AccionApertura.QUITAR_VIANDA;
  }

  public boolean esValida(String codigoTarjeta) {
    int horasParaEjecutarAccion = HorasParaEjecutarAccion.getInstance().getHorasParaEjecutarAccion();
    return !this.isAperturaConcretada() && Objects.equals(this.codigoTarjeta, codigoTarjeta)
        && LocalDateTime.now().isBefore(fecha.plusHours(horasParaEjecutarAccion));
  }
}
