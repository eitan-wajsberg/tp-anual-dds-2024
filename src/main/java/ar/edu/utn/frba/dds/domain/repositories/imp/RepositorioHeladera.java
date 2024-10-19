package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class RepositorioHeladera extends Repositorio {

  public List<Heladera> recomendarHeladeras(Direccion direccion) {
    return entityManager().createQuery("from Heladera", Heladera.class)
            .getResultList().stream()
            .filter(heladera -> heladera.getDireccion().estaCercaDe(direccion))
            .toList();
  }

  public Optional<Heladera> buscarPorId(Long id) {
    return buscarPorId(id, Heladera.class);
  }

  public List<Heladera> buscarPorNombreODireccion(String query) {
    String consulta = "FROM Heladera h WHERE h.nombre LIKE :query OR h.direccion.nomenclatura LIKE :query";

    TypedQuery<Heladera> typedQuery = entityManager().createQuery(consulta, Heladera.class);
    typedQuery.setParameter("query", "%" + query + "%");

    return typedQuery.getResultList();
  }
}