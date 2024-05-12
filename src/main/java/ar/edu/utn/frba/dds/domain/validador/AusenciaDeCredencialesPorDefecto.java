package ar.edu.utn.frba.dds.domain.validador;

public class AusenciaDeCredencialesPorDefecto implements TipoValidacion {
  @Override
  public boolean validar(String nombreUsuario, String clave) {
    return !nombreUsuario.equals(clave);
    }
  @Override
  public String getMensajeError() {
    return "La clave no debe coincidir con el nombre de usuario";
  }
}
