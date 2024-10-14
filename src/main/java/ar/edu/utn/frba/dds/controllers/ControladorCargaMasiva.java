package ar.edu.utn.frba.dds.controllers;

import static ar.edu.utn.frba.dds.utils.random.Random.generateRandomString;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.cargaMasiva.CargaMasivaColaboraciones;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
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
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.mail.MessagingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;

public class ControladorCargaMasiva implements WithSimplePersistenceUnit {
  private final String rutaCargaHbs = "/admin/adminCargaCSV.hbs";
  private RepositorioPersonaHumana repositorioPersonaHumana;
  private RepositorioUsuario repositorioUsuario;
  private RepositorioRol repositorioRol;
  private Repositorio repositorio;
  private AdapterMail adapterMail;

  public ControladorCargaMasiva(RepositorioPersonaHumana repositorioPersonaHumana, AdapterMail adapterMail, RepositorioUsuario repositorioUsuario, RepositorioRol repositorioRol, Repositorio repositorio) {
    this.repositorioPersonaHumana = repositorioPersonaHumana;
    this.adapterMail = adapterMail;
    this.repositorioUsuario = repositorioUsuario;
    this.repositorioRol = repositorioRol;
    this.repositorio = repositorio;
  }

  public void create(Context context) {
    context.render(rutaCargaHbs);
  }

  public void save(Context context) {
    UploadedFile archivo = context.uploadedFile("files");

    try {
      // Valida y obtiene el archivo de CSV
      CargaMasivaColaboraciones carga = procesarArchivoCSV(archivo);

      // Obtiene el usuario responsable
      Usuario usuarioEmisor = obtenerUsuarioEmisorResponsable();

      // Procesa cada registro del CSV
      CSVParser parser = carga.crearParserDeCsv(new InputStreamReader(new FileInputStream(carga.obtenerRutaDestino(archivo))));
      for (CSVRecord record : parser) {
        procesarRegistroCSV(record, usuarioEmisor, carga);
      }

      // Persistencia final de la carga masiva
      this.persistirCargaMasiva(carga, usuarioEmisor);

      // Renderiza éxito
      context.render(rutaCargaHbs, Map.of("success", "La carga masiva se realizó con éxito."));
    } catch (Exception e) {
      context.render(rutaCargaHbs, Map.of("error", e.getMessage()));
    }
  }

  private CargaMasivaColaboraciones procesarArchivoCSV(UploadedFile archivo) throws IOException {
    if (archivo == null) {
      throw new ValidacionFormularioException("No se ha subido un archivo.");
    }
    CargaMasivaColaboraciones carga = new CargaMasivaColaboraciones(adapterMail);
    carga.obtenerRutaDestino(archivo);
    return carga;
  }

  private Usuario obtenerUsuarioEmisorResponsable() {
    Optional<Usuario> usuarioEmisor = repositorioUsuario.buscarPorId(1L, Usuario.class);
    return usuarioEmisor.orElseThrow(() -> new ValidacionFormularioException("No se ha encontrado al usuario responsable."));
  }

  private void procesarRegistroCSV(CSVRecord record, Usuario usuarioEmisor, CargaMasivaColaboraciones carga) throws MessagingException, UnsupportedEncodingException {
    Optional<PersonaHumana> posibleHumano = repositorioPersonaHumana.buscarPorDocumento(record.get(1));

    if (posibleHumano.isPresent()) {
      // La persona ya existe, se agrega la contribución
      withTransaction(() -> persistirContribucion(record, posibleHumano.get()));
    } else {
      // La persona no existe, se crea un nuevo humano y su contribución
      Rol rolPersonaHumana = repositorioRol.buscarPorTipo(TipoRol.PERSONA_HUMANA).orElseThrow(() -> new ValidacionFormularioException("Rol de persona humana no encontrado."));
      Usuario usuario = cargarUsuario(record, rolPersonaHumana);

      PersonaHumana nuevoHumano = carga.cargarPersonaHumana(record);
      nuevoHumano.setUsuario(usuario);

      withTransaction(() -> {
        repositorioUsuario.guardar(usuario);
        repositorioPersonaHumana.guardar(nuevoHumano);
        persistirContribucion(record, nuevoHumano);

        // Enviar mensaje de alta
        Mensaje mensaje = crearMensajeAltaPersona(carga, nuevoHumano, usuarioEmisor);
        repositorio.guardar(mensaje);

        // Actualizar clave encriptada del usuario
        usuario.setClaveEncriptada(usuario.getClave());
        repositorioUsuario.actualizar(usuario);
      });
    }
  }

  private Mensaje crearMensajeAltaPersona(CargaMasivaColaboraciones carga, PersonaHumana destinatario, Usuario emisor) {
    Mensaje mensaje = carga.notificarAltaPersona(destinatario);
    mensaje.setDestinatario(destinatario.getUsuario());
    mensaje.setEmisor(emisor);
    return mensaje;
  }

  private Usuario persistirCargaMasiva(CargaMasivaColaboraciones carga, Usuario usuario) {
    withTransaction(() -> {
      carga.setResponsable(usuario);
      carga.setFechaRegistro(LocalDateTime.now());
      repositorio.guardar(carga);
    });

    return usuario;
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
        repositorio.guardar(donacionDinero);
        break;
      case DONACION_VIANDAS:
        for (int i = 0; i < cantidad; i++) {
          Vianda viandaDonada = new Vianda(fecha);
          viandaDonada.setPersonaHumana(humano);
          viandaDonada.setComida("Correspondiente a carga masiva");
          repositorio.guardar(viandaDonada);
        }
        break;
      case REDISTRIBUCION_VIANDAS:
        DistribucionVianda distribucion = new DistribucionVianda(fecha, cantidad);
        distribucion.setColaborador(humano);
        distribucion.setTerminada(true);
        distribucion.setMotivo("Carga masiva de colaboraciones");
        // distribucion.setHeladeraDestino();
        // distribucion.setHeladeraOrigen();
        repositorio.guardar(distribucion);
        break;
      case ENTREGA_TARJETAS:
        for (int i = 0; i < cantidad; i++) {
          Tarjeta tarjetaRepartida = new Tarjeta();
          tarjetaRepartida.setFechaRecepcionPersonaVulnerable(fecha);
          // como registramos a quien se la dio?
          repositorio.guardar(tarjetaRepartida);
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
