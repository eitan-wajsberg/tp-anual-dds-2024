package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.oferta.OfertaCanjeada;

import java.time.LocalDateTime;
import java.util.List;


public interface IRepositorioOfertaCanjeada {
    public void guardar(OfertaCanjeada ofertaCanjeada);
    public List<OfertaCanjeada> buscarPorFecha(LocalDateTime fecha);
}
