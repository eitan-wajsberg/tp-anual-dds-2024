package ar.edu.utn.frba.dds.utils.permisos;

import ar.edu.utn.frba.dds.domain.entities.usuarios.Permiso;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPermisos;
import java.util.Optional;

public class VerificadorDePermisos {
  private IRepositorioPermisos permisosRepository;

  public VerificadorDePermisos(IRepositorioPermisos permisosRepository) {
    this.permisosRepository = permisosRepository;
  }

  public void verificarSiUsuarioPuede(String accion, Usuario usuario) {
    Optional<Permiso> permisoBuscado = this.permisosRepository.buscar(accion);

    if(permisoBuscado.isEmpty())
      throw new RuntimeException("No existe un permiso con el nombre " + accion);

    Permiso permiso = permisoBuscado.get();

    if(!usuario.getRol().tenesPermiso(permiso))
      throw new SinPermisoSuficienteException("Usted no tiene permiso: " + accion);
  }
}
