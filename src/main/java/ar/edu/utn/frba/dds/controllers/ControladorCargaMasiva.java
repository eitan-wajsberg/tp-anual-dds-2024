package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.cargaMasiva.CargaMasivaColaboraciones;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class ControladorCargaMasiva {
  private final String rutaCargaHbs = "/admin/adminCargaCSV.hbs";
  private final String rutaListadoHbs = "/admin/adminListadoCargaCSV.hbs";
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private Repositorio repositorioCargaMasiva;
  private AdapterMail adapterMail;

  public ControladorCargaMasiva(RepositorioPersonaHumana repositorioPersonaHumana, AdapterMail adapterMail, Repositorio repositorioCargaMasiva) {
    this.repositorioPersonaHumana = repositorioPersonaHumana;
    this.adapterMail = adapterMail;
    this.repositorioCargaMasiva = repositorioCargaMasiva;
  }

  public void create(Context context) {
    context.render(rutaCargaHbs);
  }

  public void save(Context context) {
    UploadedFile dataCSV = context.uploadedFile("files");

    try {
      if (dataCSV == null) {
        throw new ValidacionFormularioException("No se ha encontrado el archivo.");
      }

      if (!dataCSV.filename().endsWith(".csv")) {
        throw new ValidacionFormularioException("El archivo no tiene extensión .csv");
      }

      String destinationPath = "tmp/" + dataCSV.filename();
      FileUtil.streamToFile(dataCSV.content(), destinationPath);

      try (Reader reader = new InputStreamReader(new FileInputStream(destinationPath))) {
        CargaMasivaColaboraciones carga = new CargaMasivaColaboraciones(repositorioPersonaHumana, adapterMail);
        carga.cargarColaboraciones(reader);
        Map<String, Object> model = new HashMap<>();
        model.put("success", "La carga masiva se realizó con éxito.");
        context.render(rutaCargaHbs, model);
      }
    } catch (Exception e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      context.render(rutaCargaHbs, model);
    }
  }
}
