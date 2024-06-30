package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioPersonaHumana implements IRepositorioPersonaHumana {
  private final List<PersonaHumana> personas;

  public RepositorioPersonaHumana(){
    this.personas = new ArrayList<>();
  }

  @Override
  public Long guardar(PersonaHumana persona) {
    persona.setId(this.personas.size() + 1);
    this.personas.add(persona);
    return persona.getId();
  }

  @Override
  public void actualizar(PersonaHumana persona) {
    for(int i=0; i < personas.size(); i++){
      if(persona.getId().equals(personas.get(i).getId())){
        personas.set(i, persona);
        break;
      }
    }
  }

  @Override
  public List<PersonaHumana> listar() {
    return personas;
  }

  @Override
  public Optional<PersonaHumana> buscarPorDocumento(Long documentoId) {
    return this.personas
        .stream()
        .filter(c -> c.getDocumento().getId().equals(documentoId))
        .findFirst();
  }
}
