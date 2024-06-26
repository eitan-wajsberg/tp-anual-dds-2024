package ar.edu.utn.frba.dds.domain.entities.personasJuridicas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rubro {
    private String nombre;
    public Rubro(String nombre) {
        this.nombre = nombre;
    }
}
