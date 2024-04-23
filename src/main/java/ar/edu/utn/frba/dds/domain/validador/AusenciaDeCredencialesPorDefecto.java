package ar.edu.utn.frba.dds.domain.validador;

public class AusenciaDeCredencialesPorDefecto implements TipoValidacion {
  public boolean validar(Usuario usuario) {
    return !usuario.getSecretoMemorizado().equals(usuario.getNombre());
    }
}
//TODO: falta implementar logica try catch