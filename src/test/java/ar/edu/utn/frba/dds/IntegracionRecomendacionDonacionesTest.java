package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class IntegracionRecomendacionDonacionesTest {
    @Test
    @DisplayName("Dar de alta heladeras")
    public void altaHeladeras() throws IOException {
        Heladeras heladeras = new Heladeras();
        HeladerasGrabadas heladerasGrabadas = RecomendacionDonacion.getInstancia().guardarHeladeras(heladeras);
        System.out.println(heladerasGrabadas);
    }

    @Test
    @DisplayName("Dar de alta personas vulnerables")
    public void altaPersonasVulnerables() throws IOException {
        Personas personas = new Personas();
        List<PersonaGrabada> personasGrabadas = RecomendacionDonacion.getInstancia().guardarPersonas(personas);
        System.out.println(personasGrabadas);
    }

    @Test
    @DisplayName("Obtener recomendaciones de heladeras")
    public void obtenerHeladeras() throws IOException {
        RecomendacionHeladeras recomendacionHeladeras = RecomendacionDonacion.getInstancia().recomendarHeladeras(3.14F, 44, "Urquiza", "400", "Santa Fe");
        System.out.println(recomendacionHeladeras);
    }

    @Test
    @DisplayName("Obtener recomendaciones de personas vulnerables")
    public void obtenerPersonasVulnerables() throws IOException {
        RecomendacionPersonasVulnerables recomendacionPersonasVulnerables = RecomendacionDonacion.getInstancia().recomendarPersonasVulnerables(3.14F, "Urquiza", "400", "Santa Fe");
        System.out.println(recomendacionPersonasVulnerables);
    }
}
