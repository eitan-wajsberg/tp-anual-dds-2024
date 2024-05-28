package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.repositories.IRepositorioPersonaHumana;
import java.util.List;
import java.util.Optional;

public class RepositorioPersonaHumana implements IRepositorioPersonaHumana {
  private List<PersonaHumana> personas;

  @Override
  public void guardar(PersonaHumana persona) {
    persona.setId((long) (this.personas.size() + 1));
    this.personas.add(persona);
  }

  @Override
  public Optional<PersonaHumana> buscarPorDocumento(Long documentoId) {
    return this.personas
        .stream()
        .filter(c -> c.getDocumento().getId().equals(documentoId))
        .findFirst();
  }
}
