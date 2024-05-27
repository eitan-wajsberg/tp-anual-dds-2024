package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import java.util.List;
import java.util.Map;

public class Tarjeta implements Contribucion {

  private String codigo;
  private List<UsoDeTarjeta> historialUsos;
  private PersonaVulnerable titular;
  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    return coeficientes.get("TARJETA_REPARTIDA");
  }
}
