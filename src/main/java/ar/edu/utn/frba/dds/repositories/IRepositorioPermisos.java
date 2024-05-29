package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.domain.usuarios.Permiso;
import java.util.Optional;

public interface IRepositorioPermisos {
  void guardar(Permiso permiso);
  Optional<Permiso> buscar(String nombre);
}
