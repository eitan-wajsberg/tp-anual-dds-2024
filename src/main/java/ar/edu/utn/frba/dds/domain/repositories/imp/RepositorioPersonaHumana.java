package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.Optional;

public class RepositorioPersonaHumana extends Repositorio {
  public Optional<PersonaHumana> buscarPorDocumento(String documentoId) {
    return entityManager()
        .createQuery("from " + PersonaHumana.class.getName() + " where nroDocumento = :dato")
        .setParameter("dato", documentoId)
        .getResultList().stream().findFirst();
      //return entityManager().createQuery("from " + PersonaHumana.class.getName() + " where nroDocumento=" + documentoId, PersonaHumana.class)
      //  .getResultList().stream().findFirst();
  }
  public Optional<PersonaHumana> buscarPorUsuario(Long idUsuario){
    return entityManager()
        .createQuery("from " + PersonaHumana.class.getName() + " WHERE usuario_id = :dato")
        .setParameter("dato", idUsuario)
        .getResultList()
        .stream()
        .findFirst();
  }
}
