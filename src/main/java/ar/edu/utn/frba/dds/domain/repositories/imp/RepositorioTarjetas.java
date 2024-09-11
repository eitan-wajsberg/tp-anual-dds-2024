package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.Optional;

public class RepositorioTarjetas extends Repositorio {
  public Optional<Tarjeta> buscarCodigo(String codigoTarjeta) {
    return entityManager().createQuery("from" + Tarjeta.class.getName() + "where codigo=" + codigoTarjeta, Tarjeta.class)
        .getResultList().stream().findFirst();
  }
}
