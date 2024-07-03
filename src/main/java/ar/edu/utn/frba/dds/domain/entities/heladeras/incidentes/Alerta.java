package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Alerta implements TipoIncidente {
    private TipoAlerta tipoAlerta;

    public String obtenerDescripcionIncidente() {
        return String.format("Se ha detectado una alerta de tipo %s.", tipoAlerta.toString());
    }
}
