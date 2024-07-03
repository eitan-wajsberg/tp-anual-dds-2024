package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

public class PersonaSinContactoException extends RuntimeException {
  public PersonaSinContactoException() {
    super("No se ha indicado un medio de contacto.");
  }
}