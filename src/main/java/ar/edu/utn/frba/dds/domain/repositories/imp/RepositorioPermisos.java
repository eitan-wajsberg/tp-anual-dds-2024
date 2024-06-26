package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Permiso;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPermisos;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioPermisos implements IRepositorioPermisos {
  private List<Permiso> permisos;

  public RepositorioPermisos(){
    permisos = new ArrayList<>();
  }

  @Override
  public void guardar(Permiso permiso) {
    permisos.add(permiso);
  }

  public Optional<Permiso> buscar(String nombre) {
    return this.permisos
        .stream()
        .filter(c -> c.getNombre().equals(nombre))
        .findFirst();
  }
}
