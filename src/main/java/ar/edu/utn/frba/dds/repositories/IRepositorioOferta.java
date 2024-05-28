package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.oferta.Oferta;

public interface IRepositorioOferta {
    void guardar(Oferta oferta);
    void actualizar(Oferta oferta);
}
