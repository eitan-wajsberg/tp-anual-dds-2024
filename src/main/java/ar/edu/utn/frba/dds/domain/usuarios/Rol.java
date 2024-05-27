package ar.edu.utn.frba.dds.domain.usuarios;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Rol {
  @Setter
  private String nombre;
  private Set<Permiso> permisos;

  public boolean tenesPermiso(Permiso permiso){
    return permisos.contains(permiso);
  }
}
