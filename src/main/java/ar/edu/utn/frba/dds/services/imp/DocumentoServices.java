package ar.edu.utn.frba.dds.services.imp;

import ar.edu.utn.frba.dds.domain.contacto.Mail;
import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.DocumentoInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.DocumentoOutputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.services.IDocumentoServices;
import ar.edu.utn.frba.dds.services.exceptions.DocumentoNoEncontradoException;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import java.util.Optional;

public class DocumentoServices implements IDocumentoServices {
  private VerificadorDePermisos verificadorDePermisos;
  private IRepositorioDocumento repoDocumento;

  public DocumentoServices(VerificadorDePermisos verificadorDePermisos, IRepositorioDocumento repoDocumento) {
    this.verificadorDePermisos = verificadorDePermisos;
    this.repoDocumento = repoDocumento;
  }

  @Override
  public DocumentoOutputDTO crear(DocumentoInputDTO documentoInputDTO, Usuario usuario) {
    verificadorDePermisos.verificarSiUsuarioPuede("CREAR-DOCUMENTO", usuario);

    // crear doc
    Documento nuevoDocumento = new Documento(TipoDocumento.valueOf(documentoInputDTO.getTipoDocumento()), documentoInputDTO.getNroDocumento());

    // guardar persona
    this.repoDocumento.guardar(nuevoDocumento);

    // generar dto salida
    DocumentoOutputDTO output = new DocumentoOutputDTO();
    output.setId(nuevoDocumento.getId());
    output.setTipoDocumento(String.valueOf(nuevoDocumento.getTipo()));
    output.setNroDocumento(nuevoDocumento.getNroDocumento());

    return output;
  }

  @Override
  public DocumentoOutputDTO modificar(Long id, DocumentoInputDTO documentoInputDTO, Usuario usuario) {
    return null; //TODO
  }

  @Override
  public void eliminar(Long id, Usuario usuario) {
    //TODO
  }

  @Override
  public void descubrirDocumento(DocumentoInputDTO documentoInputDTO, Usuario usuario) {
    verificadorDePermisos.verificarSiUsuarioPuede("BUSCAR-DOCUMENTO", usuario);

    Optional<Documento> posibleDocumento = this.repoDocumento.buscarPorNro(TipoDocumento.valueOf(documentoInputDTO.getTipoDocumento()), documentoInputDTO.getNroDocumento());
    if(posibleDocumento.isEmpty()) {
      crear(documentoInputDTO, usuario);
    }
  }
}
