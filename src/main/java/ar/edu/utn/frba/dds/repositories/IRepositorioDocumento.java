package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import java.util.Optional;

public interface IRepositorioDocumento {
  public Optional<Documento> buscar(Long id);
}
