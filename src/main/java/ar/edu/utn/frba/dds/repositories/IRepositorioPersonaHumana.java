package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.util.Optional;

public interface IRepositorioPersonaHumana {
  void guardar(PersonaHumana categoria);
  public Optional<PersonaHumana> buscarPorDocumento(Long documentoId);
}
