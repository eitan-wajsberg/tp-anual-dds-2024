package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.List;
import java.util.Optional;

public interface IRepositorioHeladera {
  void guardar(Heladera heladera);

  void actualizar(Heladera heladera);

  List<Heladera> listar();

  Optional<Heladera> buscarPorId(Long id);
}
