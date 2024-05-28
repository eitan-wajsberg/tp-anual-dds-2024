package ar.edu.utn.frba.dds.domain.usuarios;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Rol {
  @Setter
  private String nombre;
  private Set<Permiso> permisos;

  public Rol() {
    this.permisos = new HashSet<>();
  }

  public boolean tenesPermiso(Permiso permiso){
    return permisos.contains(permiso);
  }

  public void agregarPermiso(Permiso permiso){
    permisos.add(permiso);
  }

  public void quitarPermiso(Permiso permiso){
    permisos.remove(permiso);
  }
}
