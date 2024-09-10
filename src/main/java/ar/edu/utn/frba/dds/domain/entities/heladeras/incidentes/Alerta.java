package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Alerta implements TipoIncidente {
    public String obtenerDescripcionIncidente(Incidente incidente) {
        return String.format("Se ha detectado una alerta de tipo %s.", incidente.getTipoAlerta().toString());
    }
}
