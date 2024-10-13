package ar.edu.utn.frba.dds.domain.entities.cargaMasiva;

import static ar.edu.utn.frba.dds.utils.random.Random.generateRandomString;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.converters.LocalDateTimeAttributeConverter;
import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mail;
import ar.edu.utn.frba.dds.domain.entities.contacto.MedioDeContacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.documento.Documento;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.FormasContribucionHumanas;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.documento.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.entities.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.manejos.CamposObligatoriosVacios;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.Pair;

@Setter @Getter
@Entity
@Table(name = "carga_masiva")
@NoArgsConstructor
public class CargaMasivaColaboraciones {
  @Id
  @GeneratedValue
  private Long id;

  @Convert(converter = LocalDateTimeAttributeConverter.class)
  @Column(name = "fechaRegistro", nullable = false)
  private LocalDateTime fechaRegistro;

  @ManyToOne
  @JoinColumn(name="usuario_id", referencedColumnName="id", nullable = false)
  private Usuario responsable;

  @Transient
  private AdapterMail adapterMail;

  public CargaMasivaColaboraciones(AdapterMail adapterMail) {
    this.adapterMail = adapterMail;
  }

  public String obtenerRutaDestino(UploadedFile archivo) {
    if (archivo == null) {
      throw new ValidacionFormularioException("No se ha encontrado el archivo.");
    }

    if (!archivo.filename().endsWith(".csv")) {
      throw new ValidacionFormularioException("El archivo no tiene extensión .csv");
    }

    String rutaDestino = "tmp/" + archivo.filename();
    FileUtil.streamToFile(archivo.content(), rutaDestino);

    return rutaDestino;
  }

  public CSVParser crearParserDeCsv(Reader reader) {
    CSVParser csvParser;
    try {
      csvParser = CSVFormat.DEFAULT.builder()
          .setDelimiter(';')
          .setHeader()
          .setSkipHeaderRecord(true)
          .build().parse(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return csvParser;
  }

  public PersonaHumana cargarPersonaHumana(CSVRecord record) {
    String nombre = record.get(2);
    String apellido = record.get(3);
    String mail = record.get(4);
    String nroDocumento = record.get(1);

    CamposObligatoriosVacios.validarCampos(
        Pair.of("nombre", nombre),
        Pair.of("apellido", apellido),
        Pair.of("correo", mail),
        Pair.of("numero de documento", nroDocumento)
    );

    if (!mail.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
      throw new ValidacionFormularioException("El formato del correo electrónico es inválido.");
    }

    TipoDocumento tipoDocumento = TipoDocumento.valueOf(record.get(0));
    PersonaHumana persona = new PersonaHumana();
    persona.setNombre(nombre);
    persona.setApellido(apellido);

    Documento documento = new Documento(tipoDocumento, nroDocumento);
    persona.setDocumento(documento);

    Contacto contacto = new Contacto();
    contacto.setMail(mail);
    persona.setContacto(contacto);

    persona.agregarFormaDeContribucion(FormasContribucionHumanas.valueOf(record.get(6)));

    return persona;
  }

  public void notificarAltaPersona(PersonaHumana persona) {
    Mensaje mensaje = new Mensaje("Credenciales de acceso al sistema",
        "¡Gracias por colaborar en el Sistema para la Mejora del Acceso Alimentario!\n"
            + "Se le ha creado su cuenta de acceso. Sus credenciales son:\n"
            + " - Usuario: " + persona.getUsuario().getNombre() + "\n"
            + " - Clave: " + persona.getUsuario().getClave(),
        LocalDateTime.now());

    try {
      Mail mail = new Mail(adapterMail);
      mail.enviar(mensaje, persona.getContacto());
    } catch (MessagingException | UnsupportedEncodingException e) {
      throw new ValidacionFormularioException("No se ha podido notificar a " + persona.getNombre() + " " + persona.getApellido());
    }
  }
}
