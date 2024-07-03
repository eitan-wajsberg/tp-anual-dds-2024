package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import java.util.List;
import java.util.Optional;

public interface IRepositorioHeladera {
  void guardar(Heladera heladera);

  void actualizar(Heladera heladera);

  List<String> recomendarHeladeras(Direccion direccion);

  List<Heladera> listar();

  Optional<Heladera> buscarPorId(Long id);
}
