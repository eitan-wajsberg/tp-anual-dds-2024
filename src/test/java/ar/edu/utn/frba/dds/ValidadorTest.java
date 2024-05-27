package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.validador.AusenciaDeCredencialesPorDefecto;
import ar.edu.utn.frba.dds.domain.validador.ListaDePeoresClavesMemorizadas;
import ar.edu.utn.frba.dds.domain.validador.LongitudEstipulada;
import ar.edu.utn.frba.dds.domain.validador.Usuario;
import ar.edu.utn.frba.dds.domain.validador.ValidadorDeClave;
import ar.edu.utn.frba.dds.domain.validador.TipoValidacion;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorTest {

    private Usuario usuario;
    private ValidadorDeClave validador;

    @BeforeEach
    public void antesDeTestear(){
        usuario = new Usuario("Juan");

        validador = new ValidadorDeClave();
        LongitudEstipulada restriccionLongitud = new LongitudEstipulada(16);
        ListaDePeoresClavesMemorizadas restriccionLista = new ListaDePeoresClavesMemorizadas();
        AusenciaDeCredencialesPorDefecto restriccionCredenciales = new AusenciaDeCredencialesPorDefecto();

        Set<TipoValidacion> restricciones = new HashSet<>();
        restricciones.add(restriccionLongitud);
        restricciones.add(restriccionLista);
        restricciones.add(restriccionCredenciales);
        validador.setValidadores(restricciones);
    }

    @Test
    @DisplayName("El cambio de secreto falla cuando es mas corto que la longitud minima esperada")
    public void cambioDeSecretoFalla(){
        String secretoCorto = "corto";
        Assertions.assertThrows(RuntimeException.class, () -> usuario.cambiarClave(secretoCorto, validador));
    }

    @Test
    @DisplayName("El cambio de secreto es exitoso cuando se cumplen todas las condiciones")
    public void cambioDeSecretoExitoso(){
        String secreto = "hoalgajr9!!!";
        usuario.setClave(secreto);
        usuario.cambiarClave(secreto, validador);

        Assertions.assertEquals(usuario.getClave(), secreto);
    }

    @Test
    @DisplayName("El validador falla por no cumplir la longitud minima esperada")
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
