package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.usuarios.Permiso;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioPermisos;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;

public class ControladorPersonaHumana {
  private VerificadorDePermisos verificadorDePermisos;
  public void descubrirPersonaHumana(Usuario usuario, DataPersonaHumana data){
    verificadorDePermisos.verificarSiUsuarioPuede("DESCUBRIR-PERSONA-HUMANA", usuario);
  }

  public void agregarColaboracion(Usuario usuario, DataPersonaHumana data, Contribucion contribucion){
    verificadorDePermisos.verificarSiUsuarioPuede("AGREGAR-COLABORACION", usuario);
  }
  /*
  public void crearCalificacion(DataCalificacion data, Usuario usuario) {

    Permiso permisoCrearCalificaciones = RepositorioPermisos.buscar("CREAR_CALIFICACIONES");
    if(!usuario.getRol().tenesPermiso(permisoCrearCalificaciones)) {

      throw new PermisoInsuficienteException(permisoCrearCalificaciones);

    }
    Calificacion unaCalificacion = new Calificacion();
//...
    RepositorioDeCalificaciones.guardar(unaCalificacion);

  }*/
}
