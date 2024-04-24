package ar.edu.utn.frba.dds.domain.validador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListaDePeoresSecretosMemorizados implements TipoValidacion {
  @Override
  public boolean validar(Usuario usuario) {
    Path path = Paths.get("src/main/java/ar/edu/utn/frba/dds/domain/validador/ListaDeSecretosComunes.txt");
    String secreto = usuario.getSecretoMemorizado();
    try {
      return !Files.lines(path).anyMatch(linea -> linea.equals(secreto));
    } catch(IOException|SecurityException e) {
      return false;
    }
  }
  @Override
  public String getMensajeError() {
    return "El secreto no debe encontrarse entre los 10mil mas vulnerables";
  }
}
