package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.usuarios.Permiso;
import ar.edu.utn.frba.dds.repositories.IRepositorioPermisos;
import java.util.List;
import java.util.Optional;

public class RepositorioPermisos implements IRepositorioPermisos {
  private List<Permiso> permisos;
  public Optional<Permiso> buscar(String nombre) {
    return this.permisos
        .stream()
        .filter(c -> c.getNombre().equals(nombre))
        .findFirst();
  }
}
