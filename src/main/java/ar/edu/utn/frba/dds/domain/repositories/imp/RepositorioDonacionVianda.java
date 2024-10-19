package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;

public class RepositorioDonacionVianda extends Repositorio {
  public List<Vianda> buscarViandasDe(Long id) {
    return entityManager()
        .createQuery("SELECT v FROM " + Vianda.class.getName() + " v JOIN v.personaHumana p WHERE p.usuario.id = :id", Vianda.class)
        .setParameter("id", id)
        .getResultList();
  }
}
