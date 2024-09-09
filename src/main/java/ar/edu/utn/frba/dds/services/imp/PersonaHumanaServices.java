package ar.edu.utn.frba.dds.services.imp;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mail;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.entities.validador.ValidadorDeClave;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.services.exceptions.DocumentoNoEncontradoException;
import ar.edu.utn.frba.dds.services.exceptions.PersonaHumanaNoEncontradaException;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;

public class PersonaHumanaServices implements IPersonaHumanaServices {
  private IRepositorioDocumento repoDocumento;
  private IRepositorioPersonaHumana repoPersonaHumana;
  private VerificadorDePermisos verificadorDePermisos;

  public PersonaHumanaServices(IRepositorioDocumento repoDocumento, IRepositorioPersonaHumana repoPersonaHumana, VerificadorDePermisos verificadorDePermisos) {
    this.repoPersonaHumana = repoPersonaHumana;
    this.verificadorDePermisos = verificadorDePermisos;
    this.repoDocumento = repoDocumento;
  }

  public PersonaHumanaOutputDTO crear(PersonaHumanaInputDTO personaInputDTO, Usuario usuario, AdapterMail mailSender) {
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

    // TODO: no creo que esta sea el momento de añadir un contacto realmente, o que sea la mejor manera
    // agregar mail
    Contacto contacto = new Contacto();
    Mail mail = new Mail();
    mail.setAdaptador(mailSender);
    contacto.agregarMedioDeContacto(mail);
    contacto.setMail(personaInputDTO.getMail());
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
  public PersonaHumanaOutputDTO descubrirPersonaHumana(PersonaHumanaInputDTO personaInputDTO, Usuario usuario, AdapterMail mailSender) {
    verificadorDePermisos.verificarSiUsuarioPuede("BUSCAR-PERSONA-HUMANA", usuario);

    Optional<PersonaHumana> posiblePersona = this.repoPersonaHumana.buscarPorDocumento(personaInputDTO.getDocumentoId());
    PersonaHumanaOutputDTO output;

    if (posiblePersona.isEmpty()) {
      output = crear(personaInputDTO, usuario, mailSender);
    } else {
      PersonaHumana persona = posiblePersona.get();
      output = new PersonaHumanaOutputDTO();
      output.setId(persona.getId());
    }

    return output;
  }

  @Override
  public void agregarColaboraciones(PersonaHumanaInputDTO personaInputDTO, List<Contribucion> contribuciones, Usuario usuario) {
    verificadorDePermisos.verificarSiUsuarioPuede("AGREGAR-COLABORACION", usuario);
    Optional<PersonaHumana> posiblePersona = this.repoPersonaHumana.buscarPorDocumento(personaInputDTO.getDocumentoId());
    if (posiblePersona.isEmpty()) {
      throw new PersonaHumanaNoEncontradaException();
    }

    PersonaHumana persona = posiblePersona.get();
    for (Contribucion contribucion: contribuciones) {
      persona.agregarContribucion(contribucion);
    }

    this.repoPersonaHumana.actualizar(persona);
  }

}
