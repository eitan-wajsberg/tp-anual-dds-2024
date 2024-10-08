package ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion;

import java.util.List;

public class RecomendacionHeladeras {
    public List<RecomendacionHeladera> direccion;

    public class RecomendacionHeladera {
        public RecomendacionDireccion direccion;
        public RecomendacionCoordenada coordenadas;
        public int cantidad_viandas;
    }
}
