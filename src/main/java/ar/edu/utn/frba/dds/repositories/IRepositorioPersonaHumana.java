package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.util.Optional;

public interface IRepositorioPersonaHumana {
  void guardar(PersonaHumana persona);

  void actualizar(PersonaHumana persona);
  Optional<PersonaHumana> buscarPorDocumento(Long documentoId);
}
