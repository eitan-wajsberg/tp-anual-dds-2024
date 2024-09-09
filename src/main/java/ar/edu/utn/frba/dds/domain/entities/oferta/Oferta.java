package ar.edu.utn.frba.dds.domain.entities.oferta;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity @Table(name="oferta")
@NoArgsConstructor
public class Oferta{
    @Id @GeneratedValue
    private Long id;
    @Column(name ="nombre")
    private String nombre;
    @Column(name ="cantidad_puntos_necesarios")
    private float cantidadPuntosNecesarios;
    //private Object imagen;  No se especificó el tipo de imagen, fuera de dominio actual
    @ManyToOne
    @JoinColumn(name = "rubro_id", referencedColumnName = "id")
    private Rubro rubro;
    @ManyToOne
    @JoinColumn(name = "persona_juridica_id", referencedColumnName = "id")
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

