package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.utils.permisos.VerificadorDePermisos;

public class ControladorPersonaHumana {
  private VerificadorDePermisos verificadorDePermisos;
  public void descubrirPersonaHumana(Usuario usuario, PersonaHumanaInputDTO data){
    verificadorDePermisos.verificarSiUsuarioPuede("DESCUBRIR-PERSONA-HUMANA", usuario);
  }

  public void agregarColaboracion(Usuario usuario, PersonaHumanaInputDTO data, Contribucion contribucion){
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
