package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Permiso;
import java.util.Optional;

public interface IRepositorioTarjeta {
    Optional<Tarjeta> buscar(String codigoTarjeta);
}
