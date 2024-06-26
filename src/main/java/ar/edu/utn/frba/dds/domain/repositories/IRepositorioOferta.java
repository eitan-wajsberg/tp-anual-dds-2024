package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;

public interface IRepositorioOferta {
    void guardar(Oferta oferta);
    void actualizar(Oferta oferta);
}
