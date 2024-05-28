package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.ControladorCargaColaboraciones;
import ar.edu.utn.frba.dds.domain.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CargaMasivaColaboracionesTest {
  static ControladorCargaColaboraciones carga;
  static IPersonaHumanaServices personaHumanaServices;
  static IDocumentoServices documentoServices;
  static IRepositorioDocumento repoDocumento;
  static IRepositorioPersonaHumana repoPersonaHumana;
  static Usuario usuario;
  static List<PersonaHumana> personasEsperadas;

  @BeforeAll
  public static void antesDeTestear(){
    repoDocumento = new RepositorioDocumento();
    repoPersonaHumana = new RepositorioPersonaHumana();

    IRepositorioPermisos repoPermisos = new RepositorioPermisos();
    VerificadorDePermisos verificadorDePermisos = new VerificadorDePermisos(repoPermisos);

    personaHumanaServices = new PersonaHumanaServices(repoDocumento, repoPersonaHumana, verificadorDePermisos);
    documentoServices = new DocumentoServices(verificadorDePermisos, repoDocumento);
    carga = new ControladorCargaColaboraciones(personaHumanaServices, documentoServices);

    usuario = new Usuario("testing");
    Rol cargasMasivas = new Rol();
    cargasMasivas.agregarPermiso(new Permiso("CREAR-DOCUMENTO"));
    cargasMasivas.agregarPermiso(new Permiso("BUSCAR-DOCUMENTO"));
    cargasMasivas.agregarPermiso(new Permiso("CREAR-PERSONA-HUMANA"));
    cargasMasivas.agregarPermiso(new Permiso("BUSCAR-PERSONA-HUMANA"));
    cargasMasivas.agregarPermiso(new Permiso("AGREGAR-COLABORACION"));
    usuario.setRol(cargasMasivas);

    String nombreArchivo = "TestCargaMasivaColaboraciones.csv";
    File nuevoArchivo = new File("src/resources/subidas" + nombreArchivo);
    carga.cargarColaboraciones(usuario, nuevoArchivo);

    personasEsperadas = new ArrayList<>();
  }

  @Test
  @DisplayName("Se cargan todas las nuevas personas")
  public void seCarganTodasLasPersonas() {
    Assertions.assertEquals(repoPersonaHumana.listar().size(), personasEsperadas.size());
  }
  @Test
  @DisplayName("Se cargan bien todos los datos de una nueva persona")
  public void cargaCompletaDePersona(){
    PersonaHumana personaEnRepo = repoPersonaHumana.listar().get(0);
    PersonaHumana personaEsperada = new PersonaHumana();
    personaEsperada.setNombre("Marco");
    personaEsperada.setNombre("Bravo");
    personaEsperada.agregarContribucion(new Vianda(LocalDate.parse("25/05/2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    personaEsperada.agregarContribucion(new DistribucionVianda(LocalDate.parse("26/05/2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")), 5));
    personaEsperada.agregarContribucion(new Vianda(LocalDate.parse("30/05/2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    personaEsperada.agregarContribucion(new Tarjeta(LocalDate.parse("04/06/2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    float puntajeReal = ReconocimientoTrabajoRealizado.getInstance().
        calcularPuntaje(personaEnRepo.getContribuciones(), personaEnRepo.puntosGastados());
    float puntajeEsperado = ReconocimientoTrabajoRealizado.getInstance().
        calcularPuntaje(personaEsperada.getContribuciones(), personaEnRepo.puntosGastados());

    Assertions.assertEquals(personaEsperada.getNombre(), personaEnRepo.getNombre());
    Assertions.assertEquals(personaEsperada.getApellido(), personaEnRepo.getApellido());
    Assertions.assertEquals(personaEsperada.getDocumento().getTipo(), personaEnRepo.getDocumento().getTipo());
    Assertions.assertEquals(personaEsperada.getDocumento().getNroDocumento(), personaEnRepo.getDocumento().getNroDocumento());
    Assertions.assertEquals(personaEsperada.getContribuciones().size(), personaEnRepo.getContribuciones().size());
    Assertions.assertEquals(personaEsperada.getContribuciones().size(), personaEnRepo.getContribuciones().size());
    Assertions.assertEquals(puntajeEsperado, puntajeReal);
  }
}
