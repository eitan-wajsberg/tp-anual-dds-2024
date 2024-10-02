package ar.edu.utn.frba.dds.domain.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import org.hibernate.Criteria;

public class Repositorio implements WithSimplePersistenceUnit {
  public void guardar(Object objeto) {
    entityManager().persist(objeto);
  }

  public void actualizar(Object objeto) {
    entityManager().merge(objeto);
  }

  public <T> List<T> buscarTodos(Class<T> clase) {
    return entityManager().createQuery("from " + clase.getName(), clase)
        .getResultList();
  }

  public <T> void eliminarFisico(Class<T> clase, Long id) {
    String hql = "DELETE FROM " + clase.getSimpleName() + " e WHERE e.id = :id";
    entityManager().createQuery(hql)
        .setParameter("id", id)
        .executeUpdate();
  }

  public void eliminarLogico(Object objeto) {
    // BAJA LÓGICA
    // objeto.setActivo(false);
    // entityManager().merge(servicio);
  } // TODO: REVISAR si es necesario

  public <T> Optional<T> buscarPorId(Long id, Class<T> clase) {
    return entityManager()
        .createQuery("from " + clase.getName() + " where id="+ id, clase)
        .getResultList().stream().findFirst();
  }
}
