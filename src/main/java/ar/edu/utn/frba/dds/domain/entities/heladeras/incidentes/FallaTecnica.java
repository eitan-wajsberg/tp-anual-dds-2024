package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
public class FallaTecnica implements TipoIncidente {
    private PersonaHumana colaborador;
    private String descripcion;
    private Image foto;

     public String obtenerDescripcionIncidente() {
       return String.format("Hubo una falla técnica reportada por %s. Descripción: %s.",
           colaborador.getNombre(),
           descripcion);
     }
}
