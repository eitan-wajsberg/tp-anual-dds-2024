package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import java.util.List;
import java.util.Optional;

public interface IRepositorioPersonaHumana {
  void guardar(PersonaHumana persona);
  void actualizar(PersonaHumana persona);
  List<PersonaHumana> listar();
  Optional<PersonaHumana> buscarPorDocumento(Long documentoId);
}
