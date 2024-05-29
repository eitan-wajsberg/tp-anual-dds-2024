package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.ControladorCargaColaboraciones;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.personasVulnerables.Tarjeta;
import ar.edu.utn.frba.dds.domain.usuarios.Permiso;
import ar.edu.utn.frba.dds.domain.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.repositories.IRepositorioPermisos;
import ar.edu.utn.frba.dds.repositories.IRepositorioPersonaHumana;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioDocumento;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioPermisos;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.services.IDocumentoServices;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.services.imp.DocumentoServices;
import ar.edu.utn.frba.dds.services.imp.PersonaHumanaServices;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class CargaMasivaColaboracionesTest {
  static ControladorCargaColaboraciones carga;
  static IPersonaHumanaServices personaHumanaServices;
  static IDocumentoServices documentoServices;
  static IRepositorioDocumento repoDocumento;
  static IRepositorioPersonaHumana repoPersonaHumana;
  static Usuario usuario;

  // TODO: REVISAR PORQUE AGREGAMOS EL MOCK DE MAIL SENDER

  @BeforeAll
  public static void antesDeTestear() throws MessagingException, UnsupportedEncodingException {
    repoDocumento = new RepositorioDocumento();
    repoPersonaHumana = new RepositorioPersonaHumana();

    IRepositorioPermisos repoPermisos = new RepositorioPermisos();
    VerificadorDePermisos verificadorDePermisos = new VerificadorDePermisos(repoPermisos);

    personaHumanaServices = new PersonaHumanaServices(repoDocumento, repoPersonaHumana, verificadorDePermisos);
    documentoServices = new DocumentoServices(verificadorDePermisos, repoDocumento);
    carga = new ControladorCargaColaboraciones(personaHumanaServices, documentoServices, verificadorDePermisos);

    usuario = new Usuario("testing");
    Rol cargasMasivas = new Rol();

    List<Permiso> permisosNecesarios = new ArrayList<>();
    permisosNecesarios.add(new Permiso("CARGAR-MASIVAMENTE-COLABORACIONES"));
    permisosNecesarios.add(new Permiso("CREAR-DOCUMENTO"));
    permisosNecesarios.add(new Permiso("BUSCAR-DOCUMENTO"));
    permisosNecesarios.add(new Permiso("CREAR-PERSONA-HUMANA"));
    permisosNecesarios.add(new Permiso("BUSCAR-PERSONA-HUMANA"));
    permisosNecesarios.add(new Permiso("AGREGAR-COLABORACION"));
    permisosNecesarios.add(new Permiso("CREAR-USUARIO"));
    permisosNecesarios.add(new Permiso("CREAR-USUARIO"));
    for(Permiso permiso: permisosNecesarios){
      repoPermisos.guardar(permiso);
      cargasMasivas.agregarPermiso(permiso);
    }
    usuario.setRol(cargasMasivas);

    String nombreArchivo = "TestCargaMasivaColaboraciones.csv";
    File nuevoArchivo = new File("src/resources/" + nombreArchivo);

    AdapterMail mailSender = mock(AdapterMail.class);
    carga.cargarColaboraciones(usuario, nuevoArchivo, mailSender);

    doNothing().when(mailSender).enviar(any(), any());

    ReconocimientoTrabajoRealizado.getInstance();
  }

  @Test
  @DisplayName("Se cargan todas las nuevas personas")
  public void seCarganTodasLasPersonas() {
    Assertions.assertEquals(repoPersonaHumana.listar().size(), 6);
  }

  @Test
  @DisplayName("Se cargan bien todos los datos de una nueva persona")
  public void cargaCompletaDePersona(){
    PersonaHumana personaEnRepo = repoPersonaHumana.listar().get(0);
    PersonaHumana personaEsperada = new PersonaHumana();
    personaEsperada.setNombre("Marco");
    personaEsperada.setApellido("Bravo");
    personaEsperada.setDocumento(new Documento(TipoDocumento.DNI, "44125678"));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    personaEsperada.agregarContribucion(new Vianda(LocalDate.parse("25/05/2024", dateFormat)));
    personaEsperada.agregarContribucion(new DistribucionVianda(LocalDate.parse("26/05/2024", dateFormat), 5));
    personaEsperada.agregarContribucion(new Vianda(LocalDate.parse("30/05/2024", dateFormat)));
    personaEsperada.agregarContribucion(new Tarjeta(LocalDate.parse("04/06/2024", dateFormat)));
    float puntajeReal = ReconocimientoTrabajoRealizado.getInstance().
        calcularPuntaje(personaEnRepo.getContribuciones(), personaEnRepo.puntosGastados());
    float puntajeEsperado = ReconocimientoTrabajoRealizado.getInstance().
        calcularPuntaje(personaEsperada.getContribuciones(), personaEsperada.puntosGastados());

    Assertions.assertEquals(personaEsperada.getNombre(), personaEnRepo.getNombre());
    Assertions.assertEquals(personaEsperada.getApellido(), personaEnRepo.getApellido());
    Assertions.assertEquals(personaEsperada.getDocumento().getTipo(), personaEnRepo.getDocumento().getTipo());
    Assertions.assertEquals(personaEsperada.getDocumento().getNroDocumento(), personaEnRepo.getDocumento().getNroDocumento());
    Assertions.assertEquals(personaEsperada.getContribuciones().size(), personaEnRepo.getContribuciones().size());
    Assertions.assertEquals(puntajeEsperado, puntajeReal);
  }
}
