package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Alerta implements TipoIncidente {
    private Incidente incidente;
    private TipoAlertaa tipoAlertaa;

    public String obtenerDescripcionIncidente() {
        // TODO
        return null;
    }
}
