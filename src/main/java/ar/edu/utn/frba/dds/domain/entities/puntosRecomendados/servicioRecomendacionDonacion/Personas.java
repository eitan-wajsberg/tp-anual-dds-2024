package ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion;

import java.util.List;

public class Personas {
    public List<Persona> personas;

    public class Persona{
        public String nombre;
        public String apellido;
        public RecomendacionDireccion direccion;
        // public List<Persona> hijos;
    }
}
