package ar.edu.utn.frba.dds;


import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorMovimiento;
import ar.edu.utn.frba.dds.domain.adapters.AdapterSensorTemperatura;
import ar.edu.utn.frba.dds.domain.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.heladeras.Modelo;

import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;



public class HeladeraCambioDeEstadoTest {
    private  Heladera heladera;
    private AdapterSensorTemperatura adapterTemperatura;
    private  AdapterSensorMovimiento adapterSensorMovimiento;
    private final Modelo modelo = new Modelo("ModeloTest", 2.0f, 8.0f);

    @BeforeEach
    public void antesDeTestear() {
        heladera = new Heladera();
        adapterTemperatura = mock(AdapterSensorTemperatura.class);
        adapterSensorMovimiento = mock(AdapterSensorMovimiento.class);

        heladera.setAdapterTemperatura(adapterTemperatura);
        heladera.setAdapterSensorMovimiento(adapterSensorMovimiento);
        heladera.setModelo(modelo);
    }

    @Test
    @DisplayName("Cuando el Sensor de Movimiento detecta un posible Fraude el Estado cambia a FRAUDE")
    void testRecalcularEstadoFraude() {
        when(adapterSensorMovimiento.detectarFraude()).thenReturn(true);
        heladera.recalcularEstado();
        Assertions.assertEquals(EstadoHeladera.FRAUDE, heladera.getEstado());
    }

    @Test
    @DisplayName("Cuando el Sensor de Temperatura detecta que la Heladera supera su Temperatura Maxima el Estado cambia a DESPERFECTO")
    void testRecalcularEstadoDesperfectoPorTemperaturaMaxima() {
        when(adapterSensorMovimiento.detectarFraude()).thenReturn(false);
        when(adapterTemperatura.detectarTemperatura()).thenReturn(10.0f); // Temperatura por encima del máximo
        heladera.recalcularEstado();
        Assertions.assertEquals(EstadoHeladera.DESPERFECTO, heladera.getEstado());
    }

    @Test
    @DisplayName("Cuando el Sensor de Temperatura detecta que la Heladera disminuye por debajo su Temperatura Mainima el Estado cambia a DESPERFECTO")
    void testRecalcularEstadoDesperfectoPorTemperaturaMinima() {
        when(adapterSensorMovimiento.detectarFraude()).thenReturn(false);
        when(adapterTemperatura.detectarTemperatura()).thenReturn(1.0f); // Temperatura por debajo del mínimo
        heladera.recalcularEstado();
        Assertions.assertEquals(EstadoHeladera.DESPERFECTO, heladera.getEstado());
    }

    @Test
    @DisplayName("Cuando No se Detectaron posibles Fraudes y La Temperatura esta en el Rango optimo el estado es ACTIVA")
    void testRecalcularEstadoActiva() {
        when(adapterSensorMovimiento.detectarFraude()).thenReturn(false);
        when(adapterTemperatura.detectarTemperatura()).thenReturn(5.0f); // Temperatura dentro del rango
        heladera.recalcularEstado();
        Assertions.assertEquals(EstadoHeladera.ACTIVA, heladera.getEstado());
    }
}
