package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable
@NoArgsConstructor
public class Municipio {
    @Column(name = "municipio")
    private String municipio;

    public Municipio(String municipio) {
        this.municipio = municipio;
    }
}
