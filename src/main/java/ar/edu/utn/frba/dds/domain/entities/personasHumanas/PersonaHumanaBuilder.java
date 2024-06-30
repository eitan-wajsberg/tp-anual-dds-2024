package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

import static ar.edu.utn.frba.dds.utils.random.Random.generateRandomString;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
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
    this.persona.getContacto().agregarMedioDeContacto(contactoMail);
    this.mail = mail;
    return this;
  }

  public PersonaHumana construir() {
    if(mail == null){
      throw new PersonaSinContactoException();
    }

    crearUsuario(this.persona);

    return persona;
  }

  private void crearUsuario(PersonaHumana persona){
    Usuario usuarioDePersona = new Usuario(this.mail);
    String clave = generateRandomString(12);

    usuarioDePersona.cambiarClave(clave);
    System.out.println("Clave: " + clave);

    this.persona.setUsuario(usuarioDePersona);
  }
}
