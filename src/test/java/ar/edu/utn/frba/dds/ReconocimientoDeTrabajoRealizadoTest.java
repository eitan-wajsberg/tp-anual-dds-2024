package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.contacto.Mail;
import ar.edu.utn.frba.dds.domain.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
    Contribucion donacionVianda1 = new Vianda(LocalDate.parse("2024-10-29"), true,
        "Milanesa", 300, 52, LocalDate.now());
    Contribucion donacionVianda2 = new Vianda(LocalDate.parse("2024-11-20"), true,
        "Pizza", 400, 30, LocalDate.now());
    persona.agregarContribucion(donacionVianda1);
    persona.agregarContribucion(donacionVianda2);

    Path path = Paths.get("src/resources/coeficientesPuntaje.properties");
    ReconocimientoTrabajoRealizado.getInstance().cargarCoeficientesDesdeArchivo(path);

  Assertions.assertEquals(ReconocimientoTrabajoRealizado.getInstance().
      calcularPuntaje(persona.getContribuciones(), persona.puntosGastados()), 3);
  }

}


