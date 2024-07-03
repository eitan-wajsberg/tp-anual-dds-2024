package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.cargaMasiva.CargaMasivaColaboraciones;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.MailSender;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumanaBuilder;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.entities.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDocumento;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CargaMasivaColaboracionesTest {
  static CargaMasivaColaboraciones carga;
  static IRepositorioDocumento repoDocumento;
  static IRepositorioPersonaHumana repoPersonaHumana;

  @BeforeAll
  public static void antesDeTestear() throws MessagingException, UnsupportedEncodingException {
    repoDocumento = new RepositorioDocumento();
    repoPersonaHumana = new RepositorioPersonaHumana();

    // Preparo mail sender
    AdapterMail mailSender = mock(AdapterMail.class);
    doNothing().when(mailSender).enviar(any(), any());

    // Preparo archivo
    String nombreArchivo = "TestCargaMasivaColaboraciones.csv";
    File nuevoArchivo = new File("src/resources/" + nombreArchivo);

    // Realizo carga
    carga = new CargaMasivaColaboraciones(repoPersonaHumana, repoDocumento, mailSender);
    carga.cargarColaboraciones(nuevoArchivo);
  }

  @Test
  @DisplayName("Se cargan todas las nuevas personas")
  public void seCarganTodasLasPersonas() {
    Assertions.assertEquals(repoPersonaHumana.contar(), 6);
  }

  @Test
  @DisplayName("Se cargan bien todos los datos de una nueva persona")
  public void cargaCompletaDePersona(){
    PersonaHumana personaEnRepo = repoPersonaHumana.listar().get(0);
    PersonaHumana personaEsperada = construirPrimeraPersona();

    float puntajeReal = ReconocimientoTrabajoRealizado.getInstance().
        calcularPuntaje(personaEnRepo.getContribuciones(), personaEnRepo.puntosGastados());
    float puntajeEsperado = ReconocimientoTrabajoRealizado.getInstance().
        calcularPuntaje(personaEsperada.getContribuciones(), personaEsperada.puntosGastados());

    Assertions.assertTrue(personaEsperada.equals(personaEnRepo));
    Assertions.assertEquals(puntajeEsperado, puntajeReal);
  }

  private PersonaHumana construirPrimeraPersona(){
    Tarjeta tarjeta = new Tarjeta();
    tarjeta.setFechaEntrega(fecha("04/06/2024"));

    PersonaHumanaBuilder builder = new PersonaHumanaBuilder();
    PersonaHumana persona =
        builder.construirNombre("Marco")
            .construirApellido("Bravo")
            .construirMail("nosequepasa@sielmailnoesvalido.com", new MailSender())
            .construirDocumento(new Documento(TipoDocumento.DNI, "44125678"))
            .construirContribucion(new Vianda(fecha("25/05/2024")))
            .construirContribucion(new DistribucionVianda(fecha("26/05/2024"), 5))
            .construirContribucion(new Vianda(fecha("30/05/2024")))
            .construirContribucion(tarjeta)
            .construir();

    return persona;
  }

  private LocalDate fecha(String fecha){
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(fecha, dateFormat);
  }
}
