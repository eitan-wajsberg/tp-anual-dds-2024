package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.DocumentoInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.DocumentoOutputDTO;

public interface IDocumentoServices {
  DocumentoOutputDTO crear(DocumentoInputDTO documentoInputDTO, Usuario usuario);
  DocumentoOutputDTO modificar(Long id, DocumentoInputDTO documentoInputDTO, Usuario usuario);
  void eliminar(Long id, Usuario usuario);
  DocumentoOutputDTO descubrirDocumento(DocumentoInputDTO documentoInputDTO, Usuario usuario);
}
