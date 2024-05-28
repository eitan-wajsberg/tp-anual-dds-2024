package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Tarjeta implements Contribucion {
  @Setter
  private String codigo;
  private LocalDate fechaEntrega;
  private List<UsoDeTarjeta> historialUsos;
  @Setter
  private PersonaVulnerable titular;

  public Tarjeta(String codigo, LocalDate fechaEntrega, PersonaVulnerable titular) {
    this.codigo = codigo;
    this.fechaEntrega = fechaEntrega;
    this.titular = titular;
    this.historialUsos = new ArrayList<>();
  }

  public Tarjeta(LocalDate fechaEntrega){
    this.fechaEntrega = fechaEntrega;
  }

  public float calcularPuntaje() {
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return coeficientes.get("coeficienteTarjetasRepartidas");
  }

  public void agregarUso(UsoDeTarjeta uso) {
    this.historialUsos.add(uso);
  }
}
