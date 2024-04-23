package ar.edu.utn.frba.dds.domain.validador;

public class Complejidad implements TipoValidacion {
  @Override
  public boolean validar(Usuario usuario) {
    return false; //TODO implementar la complejidad
  }

  @Override
  public String getMensajeError() {
    return "La complejidad del secreto es muy baja";
  }

}

