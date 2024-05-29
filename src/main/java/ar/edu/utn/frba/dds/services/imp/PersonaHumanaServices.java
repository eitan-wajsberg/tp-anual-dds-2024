package ar.edu.utn.frba.dds.services.imp;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.contacto.Mail;
import ar.edu.utn.frba.dds.domain.contacto.MailSender;
import ar.edu.utn.frba.dds.domain.contacto.Mensaje;
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

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final SecureRandom RANDOM = new SecureRandom();

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
    Mail mail = new Mail(personaInputDTO.getMail());
    mail.setAdaptador(mailSender);
    contacto.agregarMedioDeContacto(mail);
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
      // TODO: lo siguiente es provisional y una idea para futuro. La creación y asignación de usuario no debería ser hecha de un tiro
      verificadorDePermisos.verificarSiUsuarioPuede("CREAR-USUARIO", usuario);
      Usuario usuarioDePersona = new Usuario(personaInputDTO.getMail());
      String clave = generateRandomString(12);
      // TODO: Verificar que la clave proporcionada es valida y de no ser el caso generar una nueva hasta que lo sea
      usuarioDePersona.cambiarClave(clave, new ValidadorDeClave());
      System.out.println(clave);

      // TODO: debería recuperar la persona, asignarle el usuario y actualizar la persona. A fines de lo que se quiere hacer ahora, no es necesario
      posiblePersona = this.repoPersonaHumana.buscarPorDocumento(output.getDocumentoId());
      PersonaHumana persona = posiblePersona.get();
      persona.setUsuario(usuarioDePersona);
      repoPersonaHumana.actualizar(persona);

      Mensaje mensaje = new Mensaje("Credenciales de acceso al sistema",
          "¡Gracias por colaborar en el"
              + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica!\n"
              + "Se le ha creado su cuenta de acceso al sistema. Sus credenciales son: \n"
              + "Nombre de usuario: " + usuarioDePersona.getNombre() + "\n"
              + "Contaseña: " + usuarioDePersona.getClave(),
          LocalDateTime.now());
      try {
        persona.getContacto().enviarMensaje(mensaje);
      } catch (MessagingException e) {
        System.out.println(e.getMessage());
      } catch (UnsupportedEncodingException e) {
        System.out.println(e.getMessage());
      }
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

  public static String generateRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = RANDOM.nextInt(CHARACTERS.length());
      sb.append(CHARACTERS.charAt(index));
    }
    return sb.toString();
  }

}
