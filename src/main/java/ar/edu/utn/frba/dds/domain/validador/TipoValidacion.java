package ar.edu.utn.frba.dds.domain.validador;


public interface TipoValidacion {
  boolean validar(String nombreUsuario, String clave);
    String getMensajeError();
}

