package ar.edu.utn.frba.dds.domain.validador;

public class AusenciaDeCredencialesPorDefecto implements TipoValidacion {
  @Override
  public boolean validar(String nombreUsuario, String secreto) {
    return !nombreUsuario.equals(secreto);
    }
  @Override
  public String getMensajeError() {
    return "El secreto no debe coincidir con el nombre de usuario";
  }
}
