package ar.edu.utn.frba.dds.domain.entities.personasVulnerables;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;

import java.time.LocalDate;
import java.util.ArrayList;
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

  public Tarjeta(String codigo) {
    this.codigo = codigo;
    this.historialUsos = new ArrayList<>();
  }

  public Tarjeta(LocalDate fechaEntrega){
    this.fechaEntrega = fechaEntrega;
  }

  public float calcularPuntaje() {
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteTarjetasRepartidas");
    return coeficiente;
  }

  public void agregarUso(UsoDeTarjeta uso) {
    this.historialUsos.add(uso);
  }
}
