package ar.edu.utn.frba.dds.domain.definirPackages.contribuciones;

import ar.edu.utn.frba.dds.domain.definirPackages.Direccion;
import ar.edu.utn.frba.dds.domain.definirPackages.Modelo;
import ar.edu.utn.frba.dds.domain.definirPackages.ReconocimientoTrabajoRealizado;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Heladera implements Contribucion{
  private String nombre;
  private Direccion direccion;
  private String latitud;
  private String longitud;
  private Integer cantidadViandas;
  private LocalDateTime fechaRegistro;
  private int capacidadMaximaViandas;
  private boolean activa;
  private Modelo modelo;

  public void ingresarViandas(int cantidad) {
    //TODO:
  }

  public void quitarViandas(int cantidad) {
    //TODO:
  }

  public float temperatura() {
    //TODO:
    return 0;
  }
  public float calcularPuntaje(){
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.obtenerCoeficientes();
    //TODO
    //cálculo de meses activos: historial de cambios (cambio con su fecha) --> period.between(fechaInicial, fecha)
    return 0;
  }

}
