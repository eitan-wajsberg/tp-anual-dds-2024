package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.Optional;
import javax.persistence.NoResultException;

public class RepositorioRubro extends Repositorio {
  public Rubro buscarPorNombre(String nombre) {
    Rubro rubro = (Rubro) entityManager()
        .createQuery("SELECT r FROM " + Rubro.class.getName() + " r WHERE r.nombre = :nombre")
        .setParameter("nombre", nombre)
        .getSingleResult();
    return rubro;
  }
}
