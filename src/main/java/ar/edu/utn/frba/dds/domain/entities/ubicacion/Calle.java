package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import java.util.StringTokenizer;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Calle {
    private String calle;

    public Calle(String calle) {
        this.calle = calle;
    }
}
