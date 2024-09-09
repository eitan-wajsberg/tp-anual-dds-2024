package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import javax.persistence.Embeddable;
import javax.sound.sampled.Port;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Embeddable
public class Provincia {
    private String provincia;

    public Provincia(String provincia) {
        this.provincia = provincia;
    }
}
