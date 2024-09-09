package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import java.util.List;
import java.util.Optional;

public interface IRepositorioPersonaHumana {
  Long guardar(PersonaHumana persona);
  List<PersonaHumana> listar();
  Optional<PersonaHumana> buscarPorDocumento(String documentoId);
  public void actualizar(PersonaHumana persona);
  int contar();
}
