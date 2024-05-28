package ar.edu.utn.frba.dds.domain.oferta;

import ar.edu.utn.frba.dds.domain.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.personasJuridicas.Rubro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oferta {
    private String nombre;
    private float cantidadPuntosNecesarios;
    //private Object imagen;  No se especificó el tipo de imagen, fuera de dominio actual
    private Rubro rubro;
    private PersonaJuridica organizacion;
    public void canjear() {
        // Implementación del método canjear
    }
}
