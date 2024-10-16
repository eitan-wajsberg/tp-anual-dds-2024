package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;
import java.util.Optional;

public class RepositorioPersonaVulnerable extends Repositorio {
  public Optional<List<PersonaVulnerable>> buscarPersonasDe(Long idResponsable) {
    List<PersonaVulnerable> resultados = entityManager().createQuery(
            "FROM " + PersonaVulnerable.class.getName() + " t WHERE t.personaQueLoRegistro.id = :idResponsable", PersonaVulnerable.class)
        .setParameter("idResponsable", idResponsable)
        .getResultList();
    return Optional.of(resultados);
  }
}
