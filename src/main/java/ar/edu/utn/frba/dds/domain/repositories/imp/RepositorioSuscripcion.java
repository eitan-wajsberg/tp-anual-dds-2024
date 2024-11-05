package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.TipoSuscripcion;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.Optional;

public class RepositorioSuscripcion extends Repositorio {
  public Suscripcion buscarPorTipo(Long heladeraId, Long usuarioId, Class<? extends Suscripcion> tipoClase) {
    return entityManager()
        .createQuery("SELECT s "
            + "FROM " + Suscripcion.class.getName() + " s "
            + "JOIN s.suscriptor p JOIN p.usuario u "
            + "WHERE u.id = :usuarioId AND s.heladera.id = :heladeraId "
            + "AND TYPE(s) = :tipoClase", Suscripcion.class)
        .setParameter("heladeraId", heladeraId)
        .setParameter("usuarioId", usuarioId)
        .setParameter("tipoClase", tipoClase)
        .getSingleResult();
  }
}
