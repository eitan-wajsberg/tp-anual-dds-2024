package ar.edu.utn.frba.dds.domain.validador;

public class AusenciaDeCredencialesPorDefecto {
    public boolean validar(Usuario usuario) {
        return !usuario.getSecretoMemorizado().equals(usuario.getNombre());
    }
}
//falta implementar logica try catch TODO