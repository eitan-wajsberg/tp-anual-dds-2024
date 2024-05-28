package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;

public interface IPersonaHumanaServices {
  public PersonaHumanaOutputDTO crear(PersonaHumanaInputDTO personaInputDTO, Usuario usuario);
  public PersonaHumanaOutputDTO modificar(Long id, PersonaHumanaInputDTO personaInputDTO, Usuario usuario);
  public void eliminar(Long id, Usuario usuario);
  public void descubrirPersonaHumana(PersonaHumanaInputDTO personaInputDTO, Usuario usuario);
}
