package ar.edu.utn.frba.dds.domain;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReconocimientoTrabajoRealizado {
  private static ReconocimientoTrabajoRealizado instancia;
  private static Map<String, Float> coeficientes;
  private ReconocimientoTrabajoRealizado(){}

  public static ReconocimientoTrabajoRealizado getInstance(){
    if(instancia == null){
      instancia = new ReconocimientoTrabajoRealizado();
      coeficientes = new HashMap<>();
    }
    return instancia;
  }

  public static void cargarCoeficientesDesdeArchivo(Path filePath) {
    Properties propiedades = new Properties();
    try (InputStream input = Files.newInputStream(filePath)) {
      propiedades.load(input);
      for (String nombreClave : propiedades.stringPropertyNames()) {
        String valor = propiedades.getProperty(nombreClave);
        coeficientes.put(nombreClave, Float.parseFloat(valor));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Map<String, Float> obtenerCoeficientes(){
    return coeficientes;
  }

  public float calcularPuntaje(Set<Contribucion> contribuciones, float puntajeGastado){
    float puntajeBruto = (float) 0;
    for(Contribucion contribucion : contribuciones){
      puntajeBruto+=contribucion.calcularPuntaje();
    }
    return puntajeBruto - puntajeGastado;
  }
}
