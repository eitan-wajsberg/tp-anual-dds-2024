package ar.edu.utn.frba.dds.domain.validador;


public interface TipoValidacion {
  boolean validar(String nombreUsuario, String secreto);
    String getMensajeError();
}

