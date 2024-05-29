package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.personasVulnerables.Tarjeta;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.viandas.Vianda;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.DocumentoInputDTO;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.DocumentoOutputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.services.IDocumentoServices;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ControladorCargaColaboraciones {
  private IPersonaHumanaServices personaHumanaServices;
  private IDocumentoServices documentoServices;
  private VerificadorDePermisos verificadorDePermisos;

  public ControladorCargaColaboraciones(IPersonaHumanaServices personaHumanaServices, IDocumentoServices documentoServices, VerificadorDePermisos verificadorDePermisos) {
    this.personaHumanaServices = personaHumanaServices;
    this.documentoServices = documentoServices;
    this.verificadorDePermisos = verificadorDePermisos;
  }

  public void cargarColaboraciones(Usuario usuario, File dataCSV){
    verificadorDePermisos.verificarSiUsuarioPuede("CARGAR-MASIVAMENTE-COLABORACIONES", usuario);

    Reader reader = null;
    try {
      reader = new FileReader(dataCSV);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Crear un parser CSV con el formato predeterminado
    CSVParser csvParser = null;
    try {
      //csvParser = CSVFormat.DEFAULT.parse(reader);
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
      String tipoDocumento = record.get(0);
      String nroDocumento = record.get(1);
      DocumentoInputDTO documentoIDTO = new DocumentoInputDTO();
      documentoIDTO.setTipoDocumento(tipoDocumento);
      documentoIDTO.setNroDocumento(nroDocumento);
      DocumentoOutputDTO documentoOutDTO = this.documentoServices.descubrirDocumento(documentoIDTO, usuario);

      // Aseguro existencia de la persona humana
      PersonaHumanaInputDTO personaInDTO = new PersonaHumanaInputDTO();
      personaInDTO.setDocumentoId(documentoOutDTO.getId());
      personaInDTO.setNombre(record.get(2));
      personaInDTO.setApellido(record.get(3));
      personaInDTO.setMail(record.get(4));
      // TODO el agregarle un mail a una persona humana quizás debería realizarlo otro componente y no todo el "crear" de PersonaHumanaServices
      this.personaHumanaServices.descubrirPersonaHumana(personaInDTO, usuario);

      // Le agrego la o las contribuciones a la persona
      List<Contribucion> contribuciones = new ArrayList<>();
      int cantidad = Integer.parseInt(record.get(7));
      LocalDate fecha = LocalDate.parse(record.get(5), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      switch(record.get(6)){
        case "DINERO":
          DonacionDinero donacionDinero = new DonacionDinero();
          donacionDinero.setMonto(cantidad);
          donacionDinero.setFecha(fecha);

          contribuciones.add(donacionDinero);
          break;

        case "DONACION_VIANDAS":
          Vianda viandaDonada;
          for(int i=0; i < cantidad; i++){
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
          for(int i=0; i < cantidad; i++){
            tarjetaRepartida = new Tarjeta(fecha);

            contribuciones.add(tarjetaRepartida);
          }

          break;
      }
      this.personaHumanaServices.agregarColaboraciones(personaInDTO, contribuciones, usuario);
    }
  }
}
