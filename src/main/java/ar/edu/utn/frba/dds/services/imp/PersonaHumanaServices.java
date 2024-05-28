package ar.edu.utn.frba.dds.services.imp;

import ar.edu.utn.frba.dds.domain.contacto.Mail;
import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.repositories.IRepositorioPersonaHumana;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.services.exceptions.DocumentoNoEncontradoException;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;
import java.util.Optional;

public class PersonaHumanaServices implements IPersonaHumanaServices {
  private IRepositorioDocumento repoDocumento;
  private IRepositorioPersonaHumana repoPersonaHumana;
  private VerificadorDePermisos verificadorDePermisos;

  public PersonaHumanaServices(IRepositorioDocumento repoDocumento, IRepositorioPersonaHumana repoPersonaHumana, VerificadorDePermisos verificadorDePermisos) {
    this.repoPersonaHumana = repoPersonaHumana;
    this.verificadorDePermisos = verificadorDePermisos;
    this.repoDocumento = repoDocumento;
  }

  @Override
  public PersonaHumanaOutputDTO crear(PersonaHumanaInputDTO personaInputDTO, Usuario usuario) {
    verificadorDePermisos.verificarSiUsuarioPuede("CREAR-PERSONA-HUMANA", usuario);

    // obtener documento
    Optional<Documento> posibleDocumento = this.repoDocumento.buscar(personaInputDTO.getDocumentoId());
    if(posibleDocumento.isEmpty()) {
      throw new DocumentoNoEncontradoException();
    }

    // crear persona
    PersonaHumana nuevaPersona = new PersonaHumana();
    nuevaPersona.setNombre(personaInputDTO.getNombre());
    nuevaPersona.setApellido(personaInputDTO.getApellido());
    nuevaPersona.setDocumento(posibleDocumento.get());
    Contacto contacto = new Contacto();
    contacto.agregarMedioDeContacto(new Mail(personaInputDTO.getMail())); //TODO falta pasar el mail a la instancia (por constructor o como sea)
    nuevaPersona.setContacto(contacto);

    // guardar persona
    this.repoPersonaHumana.guardar(nuevaPersona);

    // generar dto salida
    PersonaHumanaOutputDTO output = new PersonaHumanaOutputDTO();
    output.setId(nuevaPersona.getId());
    output.setNombre(nuevaPersona.getNombre());
    output.setApellido(nuevaPersona.getApellido());
    output.setDocumentoId(nuevaPersona.getDocumento().getId());

    return output;
  }

  @Override
  public PersonaHumanaOutputDTO modificar(Long id, PersonaHumanaInputDTO personaInputDTO, Usuario usuario) {
    return null;
  }

  @Override
  public void eliminar(Long id, Usuario usuario) {

  }

  @Override
  public void descubrirPersonaHumana(PersonaHumanaInputDTO personaInputDTO, Usuario usuario) {
    verificadorDePermisos.verificarSiUsuarioPuede("BUSCAR-PERSONA-HUMANA", usuario);

    Optional<PersonaHumana> posiblePersona = repoPersonaHumana.buscarPorDocumento(personaInputDTO.getDocumentoId());
    if(posiblePersona.isEmpty()) {
      PersonaHumanaOutputDTO persona = crear(personaInputDTO, usuario);
      /*persona.getContacto()...enviar("¡Gracias por colaborar en el"
          + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica!\n"
          + "Se le ha creado su cuenta de acceso al sistema. Sus credenciales son: \n"
          + "Nombre de usuario: " + persona...obtenerUsuario + "\n"
          + "Contaseña: " + persona...obtenerClave );*/
    }
  }
}
