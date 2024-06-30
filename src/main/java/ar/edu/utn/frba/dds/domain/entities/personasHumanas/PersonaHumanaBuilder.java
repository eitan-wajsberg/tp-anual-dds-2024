package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

import static ar.edu.utn.frba.dds.utils.random.Random.generateRandomString;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mail;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;

public class PersonaHumanaBuilder {
  private PersonaHumana persona = new PersonaHumana();
  private String mail = null;

  public PersonaHumanaBuilder construirNombre(String nombre) {
    this.persona.setNombre(nombre);
    return this;
  }

  public PersonaHumanaBuilder construirApellido(String apellido) {
    this.persona.setApellido(apellido);
    return this;
  }

  public PersonaHumanaBuilder construirMail(String mail, AdapterMail adapterMail) {
    Mail contactoMail = new Mail(mail);
    contactoMail.setAdaptador(adapterMail);

    Contacto contacto = new Contacto();
    contacto.agregarMedioDeContacto(contactoMail);

    this.persona.setContacto(contacto);

    this.mail = mail;
    return this;
  }

  public PersonaHumana construir() {
    if(mail == null){
      throw new PersonaSinContactoException();
    }

    crearUsuario();

    return persona;
  }

  private void crearUsuario(){
    Usuario usuarioDePersona = new Usuario(this.mail);
    String clave = generateRandomString(12);

    usuarioDePersona.cambiarClave(clave);

    this.persona.setUsuario(usuarioDePersona);
  }

  public PersonaHumanaBuilder construirDocumento(Documento doc) {
    this.persona.setDocumento(doc);
    return this;
  }

  public PersonaHumanaBuilder construirContribucion(Contribucion c) {
    this.persona.agregarContribucion(c);
    return this;
  }
}
