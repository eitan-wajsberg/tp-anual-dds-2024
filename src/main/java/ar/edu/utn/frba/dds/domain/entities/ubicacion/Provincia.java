package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import javax.sound.sampled.Port;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Provincia {
    private String provincia;

    public Provincia(String provincia) {
        this.provincia = provincia;
    }
}
