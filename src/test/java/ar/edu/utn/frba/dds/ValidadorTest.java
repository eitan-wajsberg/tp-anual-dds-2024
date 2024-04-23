package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.validador.AusenciaDeCredencialesPorDefecto;
import ar.edu.utn.frba.dds.domain.validador.ListaDePeoresSecretosMemorizados;
import ar.edu.utn.frba.dds.domain.validador.LongitudEstipulada;
import ar.edu.utn.frba.dds.domain.validador.Usuario;
import ar.edu.utn.frba.dds.domain.validador.ValidadorDeSecretosMemorizados;
import ar.edu.utn.frba.dds.domain.validador.TipoValidacion;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidadorTest {

    private Usuario usuario;
    private ValidadorDeSecretosMemorizados validador;

    @BeforeEach
    public void antesDeTestear(){
        usuario = new Usuario("Juan");

        validador = new ValidadorDeSecretosMemorizados();
        LongitudEstipulada restriccionLongitud = new LongitudEstipulada(16, 8);
        ListaDePeoresSecretosMemorizados restriccionLista = new ListaDePeoresSecretosMemorizados();
        AusenciaDeCredencialesPorDefecto restriccionCredenciales = new AusenciaDeCredencialesPorDefecto();

        Set<TipoValidacion> restricciones = new HashSet<>();
        restricciones.add(restriccionLongitud);
        restricciones.add(restriccionLista);
        restricciones.add(restriccionCredenciales);
        validador.setValidadores(restricciones);
    }

    @Test
    public void fallaValidador(){
        String secretoCorto = "corto";
        usuario.setSecretoMemorizado(secretoCorto);
        Assertions.assertThrows(RuntimeException.class, () -> validador.validar(usuario));
    }

    @Test
    public void fallaValidadorPorLongitud(){
        String secretoCorto = "corto";
        usuario.setSecretoMemorizado(secretoCorto);
        try {
            validador.validar(usuario);
        }catch(RuntimeException excepcion){
            Assertions.assertTrue(excepcion.getMessage().contains(new LongitudEstipulada(1,2).getMensajeError()));
        }
    }

    @Test
    public void secretoValido(){
        String secreto = "hoalgajr9!!!";
        usuario.setSecretoMemorizado(secreto);
        Assertions.assertTrue(validador.validar(usuario));
    }
}
