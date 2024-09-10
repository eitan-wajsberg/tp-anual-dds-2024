package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.oferta.OfertaCanjeada;

import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.time.LocalDateTime;
import java.util.List;

public class RepositorioOfertaCanjeada extends Repositorio {
    public List<OfertaCanjeada> buscarPorFecha(LocalDateTime fecha) {
        return entityManager().createQuery("from" + OfertaCanjeada.class.getName() + "fechaCanje = " + fecha).getResultList();
    }
}
