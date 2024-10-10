package ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion;

import lombok.Data;

import java.util.List;


public class Heladeras {
    public List<HeladeraGrabada> heladeras;

    @Data
    public class HeladeraGrabada {
        public int cantidad_viandas;
        public RecomendacionDireccion direccion;
    }
}
