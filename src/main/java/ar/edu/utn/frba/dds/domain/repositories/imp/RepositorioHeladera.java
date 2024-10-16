package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;
import java.util.Optional;

public class RepositorioHeladera extends Repositorio {
  public List<Heladera> recomendarHeladeras(Direccion direccion) {
    return entityManager().createQuery("from Heladera", Heladera.class)
        .getResultList().stream()
        .filter(heladera -> heladera.getDireccion().estaCercaDe(direccion))
        .toList();
  }

  public Optional<Heladera> buscarPorId(Long id){
    return buscarPorId(id, Heladera.class);
  }
}
