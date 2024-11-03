package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;

public class RepositorioDonacionVianda extends Repositorio {
  public List buscarViandasDe(Long id) {
    return entityManager()
        .createQuery("SELECT v, h.nombre, h.id FROM " + Vianda.class.getName() + " v "
            +", " + Heladera.class.getName() + " h "
            +" WHERE v.personaHumana.usuario.id = :id "
            +" and v in elements(h.viandas)")
        .setParameter("id", id)
        .getResultList();
  }
}
