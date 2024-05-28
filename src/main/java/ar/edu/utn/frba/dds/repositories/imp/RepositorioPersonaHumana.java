package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.repositories.IRepositorioPersonaHumana;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class RepositorioPersonaHumana implements IRepositorioPersonaHumana {
  private List<PersonaHumana> personas;

  public RepositorioPersonaHumana(){
    this.personas = new ArrayList<>();
  }

  @Override
  public void guardar(PersonaHumana persona) {
    persona.setId((long) (this.personas.size() + 1));
    this.personas.add(persona);
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
  public Optional<PersonaHumana> buscarPorDocumento(Long documentoId) {
    return this.personas
        .stream()
        .filter(c -> c.getDocumento().getId().equals(documentoId))
        .findFirst();
  }
}
