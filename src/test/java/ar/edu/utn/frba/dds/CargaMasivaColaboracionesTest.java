package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.ControladorCargaColaboraciones;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.repositories.IRepositorioPersonaHumana;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioDocumento;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.services.IDocumentoServices;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.services.imp.DocumentoServices;
import ar.edu.utn.frba.dds.services.imp.PersonaHumanaServices;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CargaMasivaColaboracionesTest {
  static ControladorCargaColaboraciones carga;
  static IPersonaHumanaServices personaHumanaServices;
  static IDocumentoServices documentoServices;
  static IRepositorioDocumento repoDocumento;

  @BeforeAll
  public static void antesDeTestear(){
    repoDocumento = new RepositorioDocumento();
    IRepositorioPersonaHumana repoPersonaHumana = new RepositorioPersonaHumana();
    VerificadorDePermisos verificadorDePermisos = new VerificadorDePermisos();
    
    personaHumanaServices = new PersonaHumanaServices(repoDocumento, repoPersonaHumana, );
    documentoServices = new DocumentoServices();
    carga = new ControladorCargaColaboraciones();
  }

  @Test
  @DisplayName("Un archivo .csv puede ser cargado en el sistema")
  public void unArchivoCSVpuedeSerCargadoEnElSistema() {
  }
}
