package ar.edu.utn.frba.dds.domain.entities.validador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListaDePeoresClavesMemorizadas implements TipoValidacion {
  @Override
  public boolean validar(String clave) {
    Path path = Paths.get("src/main/java/ar/edu/utn/frba/dds/domain/validador/ListaDeClavesComunes.txt");
    try {
      return !Files.lines(path).anyMatch(linea -> linea.equals(clave));
    } catch (IOException | SecurityException e) {
      return false;
    }
  }
  @Override
  public String getMensajeError() {
    return "La clave no debe encontrarse entre los 10mil mas vulnerables";
  }
}
