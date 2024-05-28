package ar.edu.utn.frba.dds.domain.oferta;

import ar.edu.utn.frba.dds.domain.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.personasJuridicas.Rubro;
import ar.edu.utn.frba.dds.repositories.IRepositorioOferta;
import ar.edu.utn.frba.dds.repositories.IRepositorioOfertaCanjeada;

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

    //public Oferta(){
        //algunos sets
    //repoOfertas.guardar(oferta);
    //}
    public void canjear(float puntos) {
        if(puntos >= cantidadPuntosNecesarios){
            LocalDateTime ahora = LocalDateTime.now();
            OfertaCanjeada ofertaCanjeada = new OfertaCanjeada(this,ahora);
            //repoOfertasCanjeadas.guardar(ofertaCanjeada);
        }
    }
}

