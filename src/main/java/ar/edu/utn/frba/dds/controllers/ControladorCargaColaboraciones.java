package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.DocumentoInputDTO;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.DocumentoOutputDTO;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.services.IDocumentoServices;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ControladorCargaColaboraciones {
  private IPersonaHumanaServices personaHumanaServices;
  private IDocumentoServices documentoServices;
  private IRepositorioDocumento documentoServices;

  public ControladorCargaColaboraciones(IPersonaHumanaServices personaHumanaServices, IDocumentoServices documentoServices) {
    this.personaHumanaServices = personaHumanaServices;
    this.documentoServices = documentoServices;
  }

  public void cargarColaboraciones(Usuario usuario, File dataCSV){
    Reader reader = null;
    try {
      reader = new FileReader(dataCSV);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Crear un parser CSV con el formato predeterminado
    CSVParser csvParser = null;
    try {
      csvParser = CSVFormat.DEFAULT.parse(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (CSVRecord record : csvParser) {
      // Aseguro existencia del documento
      String tipoDocumento = record.get(0);
      String nroDocumento = record.get(1);
      DocumentoInputDTO documentoInputDTO = new DocumentoInputDTO();
      documentoInputDTO.setTipoDocumento(tipoDocumento);
      documentoInputDTO.setTipoDocumento(nroDocumento);
      DocumentoOutputDTO documentoOutputDTO = this.documentoServices.descubrirDocumento(documentoInputDTO, usuario);

      // Aseguro existencia de la persona humana
      PersonaHumanaInputDTO persona = new PersonaHumanaInputDTO();
      persona.setDocumentoId();
      // lleno DataPersona con tipo doc, nro, nombre, apellido y mail
      controladorPersona.descubrirPersonaHumana(doc);
        ...
      TipoColaboracion colaboracion;
      switch(record.get("forma de colaboracion")){
        case "dinero":
          colaboracion = new DonacionDinero(cantidad); break;
          ...
      }
      controladorPersona.agregarColaboracionAPersona(usuario, DataPersona, colaboracion);
    }
  }
}
