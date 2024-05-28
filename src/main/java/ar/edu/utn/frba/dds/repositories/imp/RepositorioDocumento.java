package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.repositories.IRepositorioDocumento;
import java.util.List;
import java.util.Optional;

public class RepositorioDocumento implements IRepositorioDocumento {
  private List<Documento> documentos;

  @Override
  public void guardar(Documento documento) {
    documento.setId((long) (this.documentos.size() + 1));
    this.documentos.add(documento);
  }

  public Optional<Documento> buscar(Long id) {
    return this.documentos
        .stream()
        .filter(c -> c.getId().equals(id))
        .findFirst();
  }

  public Optional<Documento> buscarPorNro(TipoDocumento tipo, String nro) {
    return this.documentos
        .stream()
        .filter(c -> c.getTipo().equals(tipo) && c.getNroDocumento().equals(nro))
        .findFirst();
  }
}
