package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;

public class RepositorioDistribucionVianda extends Repositorio {
  public List buscarDistribuciones(Long colaboradorId) {
    return entityManager()
          .createQuery("SELECT d FROM " + DistribucionVianda.class.getName() + " d WHERE d.colaborador.id = :colaboradorId "
              +"order by d.fecha desc, d.id desc")
          .setParameter("colaboradorId", colaboradorId)
          .getResultList();
  }
}