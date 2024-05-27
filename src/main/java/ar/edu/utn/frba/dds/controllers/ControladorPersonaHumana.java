package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.usuarios.Permiso;
import ar.edu.utn.frba.dds.repositories.imp.RepositorioPermisos;

public class ControladorPersonaHumana {
  public void descubrirPersonaHumana(DataPersonaHumana data){
    Permiso permisoCrearCalificaciones = RepositorioPermisos.buscar("CREAR_CALIFICACIONES");

  }

  public void agregarColaboracion(Usuario usuario, DataPersonaHumana data, Contribucion contribucion){

  }
  public void crearCalificacion(DataCalificacion data, Usuario usuario) {

    Permiso permisoCrearCalificaciones = RepositorioPermisos.buscar("CREAR_CALIFICACIONES");
    if(!usuario.getRol().tenesPermiso(permisoCrearCalificaciones)) {

      throw new PermisoInsuficienteException(permisoCrearCalificaciones);

    }
    Calificacion unaCalificacion = new Calificacion();
//...
    RepositorioDeCalificaciones.guardar(unaCalificacion);

  }
}
