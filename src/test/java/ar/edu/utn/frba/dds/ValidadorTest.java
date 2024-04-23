package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.validador.AusenciaDeCredencialesPorDefecto;
import ar.edu.utn.frba.dds.domain.validador.ListaDePeoresSecretosMemorizados;
import ar.edu.utn.frba.dds.domain.validador.LongitudEstipulada;
import ar.edu.utn.frba.dds.domain.validador.Usuario;
import ar.edu.utn.frba.dds.domain.validador.ValidadorDeSecretosMemorizados;
import ar.edu.utn.frba.dds.domain.validador.TipoValidacion;
import java.util.HashSet;
import java.util.Set;

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
        Complejidad restriccionComplejidad = new Complejidad();
        AusenciaDeCredencialesPorDefecto restriccionCredenciales = new AusenciaDeCredencialesPorDefecto();

        Set<TipoValidacion> restricciones = new HashSet<>();
        restricciones.add(restriccionLongitud);
        restricciones.add(restriccionLista);
        restricciones.add(restriccionComplejidad);
        restricciones.add(restriccionCredenciales);
        validador.setValidadores(restricciones);
    }

    @Test
    public void fallaLongitud(){
        // TODO para cuando esté implementado cómo se van a lanzar las excepciones
        //Assertions.assertThrows(Exception.class, () -> validador.validar(usuario));
    }
}
