package ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion;

import java.util.List;

public class RecomendacionPersonasVulnerables {
    List<RecomendacionPersonasVulnerable> recomendaciones;

    public class RecomendacionPersonasVulnerable {
        public String nombre;
        public String apellido;
        public RecomendacionDireccion direccion;
        public RecomendacionCoordenada coordenadas;
        public int cantidad_recomendada;
    }

}
