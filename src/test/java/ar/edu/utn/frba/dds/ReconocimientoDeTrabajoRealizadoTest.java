package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.contacto.Mail;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReconocimientoDeTrabajoRealizadoTest {

  @BeforeEach
  public void antesDeTestear(){

  }

  @Test
  @DisplayName("El reconocimiento lee correctamente el archivo")
  public void reconocimientoLeeArchivo() {
    Path path = Paths.get("src/resources/coeficientesPuntaje.properties");

    ReconocimientoTrabajoRealizado.getInstance().cargarCoeficientesDesdeArchivo(path);
    Map<String, Float> coeficientes = ReconocimientoTrabajoRealizado.getInstance().obtenerCoeficientes();
    Assertions.assertEquals(coeficientes.get("coeficienteTarjetasRepartidas"), 2);
    Assertions.assertEquals(coeficientes.get("coeficienteViandasDonadas"), (float)1.5);
    Assertions.assertEquals(coeficientes.get("coeficienteViandasDistribuidas"), 1);
  }
  
  @Test
  @DisplayName("Una persona que dona dos viandas tiene puntaje tres")
  public void unaPersonaQueDonaDosViandasTienePuntajeTres() {
    PersonaHumana persona = new PersonaHumana();
    // TODO: falta que este completo el resto de clases para hacer este test
  }

}


