package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioHeladera implements IRepositorioHeladera {
  private final List<Heladera> heladeras;

  public RepositorioHeladera() {
    this.heladeras = new ArrayList<>();
  }

  public void guardar(Heladera heladera) {
    heladera.setId((long) (this.heladeras.size() + 1));
    this.heladeras.add(heladera);
  }

  public void actualizar(Heladera heladera) {
    for (int i = 0; i < heladeras.size(); i++) {
      if (heladera.getId().equals(heladeras.get(i).getId())) {
        heladeras.set(i, heladera);
        break;
      }
    }
  }

  public List<String> recomendarHeladeras(Direccion direccion) {
    return this.heladeras.stream()
        .filter(heladera -> heladera.getDireccion().estaCercaDe(direccion))
        .map(Heladera::getNombre).toList();
  }

  public List<Heladera> listar() {
    return heladeras;
  }

  public Optional<Heladera> buscarPorId(Long id) {
    return this.heladeras
        .stream()
        .filter(heladera -> heladera.getId().equals(id))
        .findFirst();
  }
}
