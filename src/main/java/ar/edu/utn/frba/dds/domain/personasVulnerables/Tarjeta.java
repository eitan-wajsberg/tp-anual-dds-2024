package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Tarjeta implements Contribucion {
  @Getter @Setter
  private String codigo;
  @Getter
  private List<UsoDeTarjeta> historialUsos;
  @Getter @Setter
  private PersonaVulnerable titular;

  public Tarjeta(String codigo, PersonaVulnerable titular) {
    this.codigo = codigo;
    this.titular = titular;
    this.historialUsos = new ArrayList<>();
  }

  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return coeficientes.get("coeficienteTarjetasRepartidas");
  }
}
