package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
public class FallaTecnica implements TipoIncidente {
     public String obtenerDescripcionIncidente(Incidente incidente) {
       return String.format("Hubo una falla técnica reportada por %s. Descripción: %s.",
           incidente.getColaborador().getNombre(),
           incidente.getDescripcionDelColaborador());
     }
}
