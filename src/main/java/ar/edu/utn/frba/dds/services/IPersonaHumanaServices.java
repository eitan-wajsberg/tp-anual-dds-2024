package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.Contribucion;
import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.dtos.outputs.personasHumanas.PersonaHumanaOutputDTO;

import java.util.List;

public interface IPersonaHumanaServices {
  PersonaHumanaOutputDTO crear(PersonaHumanaInputDTO personaInputDTO, Usuario usuario, AdapterMail mailSender);
  PersonaHumanaOutputDTO modificar(Long id, PersonaHumanaInputDTO personaInputDTO, Usuario usuario);
  void eliminar(Long id, Usuario usuario);
  PersonaHumanaOutputDTO descubrirPersonaHumana(PersonaHumanaInputDTO personaInputDTO, Usuario usuario, AdapterMail mailSender);
  void agregarColaboraciones(PersonaHumanaInputDTO personaInputDTO, List<Contribucion> contribuciones, Usuario usuario);
}
