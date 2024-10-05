package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.Optional;

public class RepositorioUsuario extends Repositorio {
  public boolean existeUsuarioPorNombre(String nombre) {
    Long count = (Long) entityManager()
        .createQuery("SELECT COUNT(u) FROM " + PersonaHumana.class.getName() + " u WHERE u.nombre = :nombre")
        .setParameter("nombre", nombre)
        .getSingleResult();
    return count > 0;
  }
}