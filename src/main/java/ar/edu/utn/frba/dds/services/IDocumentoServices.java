package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.DocumentoInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.DocumentoOutputDTO;

public interface IDocumentoServices {
  public DocumentoOutputDTO crear(DocumentoInputDTO documentoInputDTO, Usuario usuario);
  public DocumentoOutputDTO modificar(Long id, DocumentoInputDTO documentoInputDTO, Usuario usuario);
  public void eliminar(Long id, Usuario usuario);
  public void descubrirDocumento(DocumentoInputDTO documentoInputDTO, Usuario usuario);
}
