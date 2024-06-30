package ar.edu.utn.frba.dds.domain.entities.cargaMasiva;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumanaBuilder;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import lombok.Setter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Setter
public class CargaMasivaColaboraciones {
  private IRepositorioPersonaHumana personaHumanaRepo;
  private IRepositorioDocumento documentoRepo;

  public CargaMasivaColaboraciones(IRepositorioPersonaHumana personaHumanaRepo, IRepositorioDocumento documentoRepo) {
    this.personaHumanaRepo = personaHumanaRepo;
    this.documentoRepo = documentoRepo;
  }

  public void cargarColaboraciones(File dataCSV, AdapterMail adapterMail) {
    Reader reader;
    try {
      reader = new FileReader(dataCSV);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Crear un parser CSV con el formato predeterminado
    CSVParser csvParser;
    try {
      csvParser = CSVFormat.DEFAULT.builder()
          .setDelimiter(';')
          .setHeader()
          .setSkipHeaderRecord(true)  // skip header
          .build().parse(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (CSVRecord record : csvParser) {
      System.out.println(record);

      // Aseguro existencia del documento
      TipoDocumento tipoDocumento = TipoDocumento.valueOf(record.get(0));
      String nroDocumento = record.get(1);

      Long idDoc = descubrirDocumento(tipoDocumento, nroDocumento);

      // Aseguro existencia de la persona humana
      Optional<PersonaHumana> posiblePersona = this.personaHumanaRepo.buscarPorDocumento(idDoc);
      PersonaHumana persona;

      if (posiblePersona.isEmpty()) {
        PersonaHumanaBuilder builder = new PersonaHumanaBuilder();
        String nombre = record.get(2);
        String apellido = record.get(3);
        String mail = record.get(4);
        persona = builder.construirNombre(nombre)
            .construirApellido(apellido)
            .construirMail(mail, adapterMail).construir();
        this.personaHumanaRepo.guardar(persona);
        notificarAltaPersona(persona);
      } else {
        persona = posiblePersona.get();
      }

      // Le agrego la o las contribuciones a la persona
      List<Contribucion> contribuciones = new ArrayList<>();
      int cantidad = Integer.parseInt(record.get(7));
      LocalDate fecha = LocalDate.parse(record.get(5), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      switch (record.get(6)) {
        case "DINERO":
          DonacionDinero donacionDinero = new DonacionDinero();
          donacionDinero.setMonto(cantidad);
          donacionDinero.setFecha(fecha);

          contribuciones.add(donacionDinero);
          break;

        case "DONACION_VIANDAS":
          Vianda viandaDonada;
          for (int i = 0; i < cantidad; i++) {
            viandaDonada = new Vianda(fecha);

            contribuciones.add(viandaDonada);
          }

          break;

        case "REDISTRIBUCION_VIANDAS":
          DistribucionVianda distribucion = new DistribucionVianda(fecha, cantidad);

          contribuciones.add(distribucion);
          break;

        case "ENTREGA_TARJETAS":
          Tarjeta tarjetaRepartida;
          for (int i = 0; i < cantidad; i++) {
            tarjetaRepartida = new Tarjeta(fecha);

            contribuciones.add(tarjetaRepartida);
          }

          break;
      }

      for (Contribucion contribucion: contribuciones) {
        persona.agregarContribucion(contribucion);
      }

      this.personaHumanaRepo.actualizar(persona);
    }
  }

  private Long descubrirDocumento(TipoDocumento tipoDocumento, String nroDocumento){
    Optional<Documento> posibleDocumento = this.documentoRepo.buscarPorNro(tipoDocumento, nroDocumento);
    Documento documento;
    Long id;

    if (posibleDocumento.isEmpty()) {
      documento = new Documento(tipoDocumento, nroDocumento);
      id = this.documentoRepo.guardar(documento);
    } else {
      documento = posibleDocumento.get();
      id = documento.getId();
    }

    return id;
  }

  private void notificarAltaPersona(PersonaHumana persona) {
    Mensaje mensaje = new Mensaje("Credenciales de acceso al sistema",
        "¡Gracias por colaborar en el"
            + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica!\n"
            + "Se le ha creado su cuenta de acceso al sistema. Sus credenciales son: \n"
            + "Nombre de usuario: " + persona.getUsuario().getNombre() + "\n"
            + "Contaseña: " + persona.getUsuario().getClave(),
        LocalDateTime.now());
    try {
      persona.getContacto().enviarMensaje(mensaje);
    } catch (MessagingException e) {
      System.out.println(e.getMessage());
    } catch (UnsupportedEncodingException e) {
      System.out.println(e.getMessage());
    }
  }
}
