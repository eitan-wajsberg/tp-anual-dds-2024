package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import java.util.List;
import java.util.Optional;

public class RepositorioDocumento implements IRepositorioDocumento {
  private List<Documento> documentos;
  public Optional<Documento> buscar(Long id) {
    return this.documentos
        .stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();
  }
}
