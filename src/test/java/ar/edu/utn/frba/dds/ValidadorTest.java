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
        LongitudEstipulada restriccionLongitud = new LongitudEstipulada(16);
        ListaDePeoresSecretosMemorizados restriccionLista = new ListaDePeoresSecretosMemorizados();
        AusenciaDeCredencialesPorDefecto restriccionCredenciales = new AusenciaDeCredencialesPorDefecto();

        Set<TipoValidacion> restricciones = new HashSet<>();
        restricciones.add(restriccionLongitud);
        restricciones.add(restriccionLista);
        restricciones.add(restriccionCredenciales);
        validador.setValidadores(restricciones);
    }

    @Test
    public void cambioDeSecretoFalla(){
        String secretoCorto = "corto";
        Assertions.assertThrows(RuntimeException.class, () -> usuario.cambiarSecreto(secretoCorto, validador));
    }

    @Test
    public void cambioDeSecretoExitoso(){
        String secreto = "hoalgajr9!!!";
        usuario.setSecretoMemorizado(secreto);
        usuario.cambiarSecreto(secreto, validador);

        Assertions.assertEquals(usuario.getSecretoMemorizado(), secreto);
    }

    @Test
    public void fallaValidadorPorLongitud(){
        String secretoCorto = "admin";
        String mensajeEsperado = new LongitudEstipulada(16).getMensajeError();
        try {
            validador.validar(usuario.getNombre(),secretoCorto);
        }catch(RuntimeException excepcion){
            Assertions.assertTrue(excepcion.getMessage().contains(mensajeEsperado));
        }
    }

}
