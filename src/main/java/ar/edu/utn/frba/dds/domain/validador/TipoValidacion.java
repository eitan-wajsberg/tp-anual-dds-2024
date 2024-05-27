package ar.edu.utn.frba.dds.domain.validador;


public interface TipoValidacion {
  boolean validar(String clave);
    String getMensajeError();
}

