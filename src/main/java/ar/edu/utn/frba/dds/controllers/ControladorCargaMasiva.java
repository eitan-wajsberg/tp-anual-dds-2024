package ar.edu.utn.frba.dds.controllers;

import static ar.edu.utn.frba.dds.utils.random.Random.generateRandomString;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.cargaMasiva.CargaMasivaColaboraciones;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.documento.Documento;
import ar.edu.utn.frba.dds.domain.entities.documento.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.FormasContribucionHumanas;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion.Personas;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.entities.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRol;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioUsuario;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;

public class ControladorCargaMasiva implements WithSimplePersistenceUnit {
  private final String rutaCargaHbs = "/admin/adminCargaCSV.hbs";
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private RepositorioUsuario repositorioUsuario;
  private RepositorioRol repositorioRol;
  private AdapterMail adapterMail;

  public ControladorCargaMasiva(RepositorioPersonaHumana repositorioPersonaHumana, AdapterMail adapterMail, RepositorioUsuario repositorio, RepositorioRol repositorioRol) {
    this.repositorioPersonaHumana = repositorioPersonaHumana;
    this.adapterMail = adapterMail;
    this.repositorioUsuario = repositorio;
    this.repositorioRol = repositorioRol;
  }

  public void create(Context context) {
    context.render(rutaCargaHbs);
  }

  public void save(Context context) {
    UploadedFile archivo = context.uploadedFile("files");

    try {
      CargaMasivaColaboraciones carga = new CargaMasivaColaboraciones(adapterMail);
      String rutaDestino = carga.obtenerRutaDestino(archivo);

      Reader reader = new InputStreamReader(new FileInputStream(rutaDestino));
      CSVParser parser = carga.crearParserDeCsv(reader);

      for (CSVRecord record : parser) {
        Optional<PersonaHumana> posibleHumano = repositorioPersonaHumana.buscarPorDocumento(record.get(1));
        Optional<Rol> rol = repositorioRol.buscarPorTipo(TipoRol.PERSONA_HUMANA);
        Usuario usuario = this.cargarUsuario(record, rol.orElse(null));

        if (posibleHumano.isEmpty()) {
          PersonaHumana humano = carga.cargarPersonaHumana(record);
          humano.setUsuario(usuario);

          withTransaction(() -> {
            repositorioUsuario.guardar(usuario);
            repositorioPersonaHumana.guardar(humano);
            persistirContribucion(record, humano);
          });

          carga.notificarAltaPersona(humano);
          usuario.setClaveEncriptada(usuario.getClave());
          repositorioUsuario.actualizar(usuario);
        } else {
          withTransaction(() -> persistirContribucion(record, posibleHumano.get()));
        }
      }

      this.persistirCargaMasiva(carga);
      context.render(rutaCargaHbs, Map.of("success", "La carga masiva se realizó con éxito.", "cargando", false));
    } catch (Exception e) {
      context.render(rutaCargaHbs, Map.of("error", e.getMessage(), "cargando", false));
    }
  }

  private void persistirCargaMasiva(CargaMasivaColaboraciones carga) {
    withTransaction(() -> {
      // TODO: Sacar el ID del usuario de la sesion
      Optional<Usuario> usuario = this.repositorioUsuario.buscarPorId(1L, Usuario.class);
      if (usuario.isEmpty()) {
        throw new ValidacionFormularioException("No se ha encontrado al usuario responsable.");
      }
      carga.setResponsable(usuario.get());
      carga.setFechaRegistro(LocalDateTime.now());
      repositorioRol.guardar(carga);
    });
  }

  private void persistirContribucion(CSVRecord record, PersonaHumana humano) {
    int cantidad = Integer.parseInt(record.get(7));
    LocalDate fecha = LocalDate.parse(record.get(5), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    switch (FormasContribucionHumanas.valueOf(record.get(6))) {
      case DONACION_DINERO:
        DonacionDinero donacionDinero = new DonacionDinero();
        donacionDinero.setMonto(cantidad);
        donacionDinero.setFecha(fecha);
        donacionDinero.setPersonaHumana(humano);
        repositorioRol.guardar(donacionDinero);
        break;
      case DONACION_VIANDAS:
        for (int i = 0; i < cantidad; i++) {
          Vianda viandaDonada = new Vianda(fecha);
          viandaDonada.setPersonaHumana(humano);
          viandaDonada.setComida("Correspondiente a carga masiva");
          repositorioRol.guardar(viandaDonada);
        }
        break;
      case REDISTRIBUCION_VIANDAS:
        DistribucionVianda distribucion = new DistribucionVianda(fecha, cantidad);
        distribucion.setColaborador(humano);
        distribucion.setTerminada(true);
        distribucion.setMotivo("Carga masiva de colaboraciones");
        // distribucion.setHeladeraDestino();
        // distribucion.setHeladeraOrigen();
        repositorioRol.guardar(distribucion);
        break;
      case ENTREGA_TARJETAS:
        for (int i = 0; i < cantidad; i++) {
          Tarjeta tarjetaRepartida = new Tarjeta();
          tarjetaRepartida.setFechaRecepcionPersonaVulnerable(fecha);
          // como registramos a quien se la dio?
          repositorioRol.guardar(tarjetaRepartida);
        }
        break;
      default:
        throw new ValidacionFormularioException("Tipo de contribución no válida: " + record.get(6));
    }
  }

  public Usuario cargarUsuario(CSVRecord record, Rol rol) {
    String baseNombre = record.get(2).toLowerCase() + "." + record.get(3).toLowerCase();
    String nombreUsuario = baseNombre;
    int contador = 1;

    while (repositorioUsuario.buscarPorNombre(nombreUsuario).isPresent()) {
      nombreUsuario = baseNombre + contador++;
    }

    Usuario usuario = new Usuario();
    usuario.setNombre(nombreUsuario);
    usuario.setClave(generateRandomString(12));
    usuario.setRol(rol);

    return usuario;
  }
}
