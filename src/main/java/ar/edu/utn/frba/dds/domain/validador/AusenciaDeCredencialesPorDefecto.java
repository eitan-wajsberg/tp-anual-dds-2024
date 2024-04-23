package ar.edu.utn.frba.dds.domain.validador;

public class AusenciaDeCredencialesPorDefecto implements TipoValidacion {
  @Override
  public boolean validar(Usuario usuario) {
    return !usuario.getSecretoMemorizado().equals(usuario.getNombre());
    }
  @Override
  public String getMensajeError() {
    return "El secreto no debe coincidir con el nombre de usuario";
  }
}
