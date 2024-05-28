package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.personasHumanas.TipoDocumento;
import java.util.Optional;

public interface IRepositorioDocumento {
  void guardar(Documento documento);
  Optional<Documento> buscar(Long id);
  Optional<Documento> buscarPorNro(TipoDocumento tipo, String nro);

}
