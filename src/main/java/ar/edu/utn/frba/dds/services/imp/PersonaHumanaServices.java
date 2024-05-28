package ar.edu.utn.frba.dds.services.imp;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.contacto.Mail;
import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.validador.ValidadorDeClave;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import ar.edu.utn.frba.dds.repositories.IRepositorioPersonaHumana;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.services.exceptions.DocumentoNoEncontradoException;
import ar.edu.utn.frba.dds.services.exceptions.PersonaHumanaNoEncontradaException;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;

import java.util.List;
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

    // TODO: no creo que esta sea el momento de añadir un contacto realmente, o que sea la mejor manera
    // agregar mail
    Contacto contacto = new Contacto();
    contacto.agregarMedioDeContacto(new Mail(personaInputDTO.getMail()));
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
  public PersonaHumanaOutputDTO descubrirPersonaHumana(PersonaHumanaInputDTO personaInputDTO, Usuario usuario) {
    verificadorDePermisos.verificarSiUsuarioPuede("BUSCAR-PERSONA-HUMANA", usuario);

    Optional<PersonaHumana> posiblePersona = this.repoPersonaHumana.buscarPorDocumento(personaInputDTO.getDocumentoId());
    PersonaHumanaOutputDTO output;

    if(posiblePersona.isEmpty()) {
      output = crear(personaInputDTO, usuario);
      // TODO: lo siguiente es provisional y una idea para futuro. La creación y asignación de usuario no debería ser hecha de un tiro
      verificadorDePermisos.verificarSiUsuarioPuede("CREAR-USUARIO", usuario);
      Usuario usuarioDePersona = new Usuario(personaInputDTO.getMail());
      usuarioDePersona.cambiarClave("nuevaClave2024", new ValidadorDeClave());

      // TODO: debería recuperar la persona, asignarle el usuario y actualizar la persona. A fines de lo que se quiere hacer ahora, no es necesario
      posiblePersona = this.repoPersonaHumana.buscarPorDocumento(output.getDocumentoId());
      PersonaHumana persona = posiblePersona.get();

      // TODO IMPORTANTE: encontrar una manera de que el mensaje de envíe específicamente por mail
      /*persona.getContacto()...enviar("¡Gracias por colaborar en el"
          + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica!\n"
          + "Se le ha creado su cuenta de acceso al sistema. Sus credenciales son: \n"
          + "Nombre de usuario: " + usuarioDePersona.getNombre() + "\n"
          + "Contaseña: " + usuarioDePersona.getClave() );*/
    }else{
      PersonaHumana persona = posiblePersona.get();
      output = new PersonaHumanaOutputDTO();
      output.setId(persona.getId());
    }

    return output;
  }

  @Override
  public void agregarColaboraciones(PersonaHumanaInputDTO personaInputDTO, List<Contribucion> contribuciones, Usuario usuario){
    verificadorDePermisos.verificarSiUsuarioPuede("AGREGAR-COLABORACION", usuario);
    Optional<PersonaHumana> posiblePersona = this.repoPersonaHumana.buscarPorDocumento(personaInputDTO.getDocumentoId());
    if(posiblePersona.isEmpty()) {
      throw new PersonaHumanaNoEncontradaException();
    }

    PersonaHumana persona = posiblePersona.get();
    for(Contribucion contribucion: contribuciones){
      persona.agregarContribucion(contribucion);
    }

    this.repoPersonaHumana.actualizar(persona);
  }
}
