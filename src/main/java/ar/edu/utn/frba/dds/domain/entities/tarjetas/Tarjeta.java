package ar.edu.utn.frba.dds.domain.entities.tarjetas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Tarjeta implements Contribucion {
  @Setter
  private String codigo;
  @Setter
  private LocalDate fechaEntrega;
  private List<UsoDeTarjeta> historialUsos;
  private static final int HASH_LENGTH = 11;

  public Tarjeta() {
    this.historialUsos = new ArrayList<>();
    this.codigo = generarCodigo();
  }

  public Tarjeta(LocalDate fechaEntrega){
    this.fechaEntrega = fechaEntrega;
  }

  public float calcularPuntaje() {
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteTarjetasRepartidas");
    return coeficiente;
  }

  public TipoContribucion obtenerTipoContribucion() {
    return TipoContribucion.ENTREGA_TARJETAS;
  }

  public LocalDate obtenerFechaRegistro() {
    return null;
  }

  public void agregarUso(UsoDeTarjeta uso) {
    this.historialUsos.add(uso);
  }

  public static String generarCodigo() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] randomBytes = new byte[8];
    secureRandom.nextBytes(randomBytes);
    String hash = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    return hash.length() > HASH_LENGTH ? hash.substring(0, HASH_LENGTH) : hash;
  }
}
