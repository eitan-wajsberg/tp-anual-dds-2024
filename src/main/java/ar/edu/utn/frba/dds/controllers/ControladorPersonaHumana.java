package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;

public class ControladorPersonaHumana {
  private IPersonaHumanaServices personaHumanaServices;
  public void descubrirPersonaHumana(Object data){
    PersonaHumanaInputDTO dto = (PersonaHumanaInputDTO) data;

    // TODO obtener usuario
    Usuario usuarioActual = null;

    personaHumanaServices.descubrirPersonaHumana(dto, usuarioActual);
  }

  public void agregarColaboracion(Usuario usuario, PersonaHumanaInputDTO data, Contribucion contribucion){}
}
