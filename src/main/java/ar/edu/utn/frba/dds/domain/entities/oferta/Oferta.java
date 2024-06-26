package ar.edu.utn.frba.dds.domain.entities.oferta;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Oferta {
    private Long id;
    private String nombre;
    private float cantidadPuntosNecesarios;
    //private Object imagen;  No se especificó el tipo de imagen, fuera de dominio actual
    private Rubro rubro;
    private PersonaJuridica organizacion;

    public Oferta(Long id, String nombre, float puntosNecesarios, Rubro rubro, PersonaJuridica org){
        this.id= id;
        this.nombre = nombre;
        this.cantidadPuntosNecesarios = puntosNecesarios;
        this.rubro = rubro;
        this.organizacion = org;
    }
    public void canjear(PersonaHumana persona) {
        if(persona.calcularPuntajeNeto() >= cantidadPuntosNecesarios){
            LocalDateTime ahora = LocalDateTime.now();
            OfertaCanjeada ofertaCanjeada = new OfertaCanjeada(this,ahora);
            persona.agregarOfertaCanjeada(ofertaCanjeada);
        }
    }
}

