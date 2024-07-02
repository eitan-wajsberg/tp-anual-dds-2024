package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;

import java.util.List;

public interface IRepositorioTecnicos {
    Long guardar(Tecnico tecnico);
    List<Tecnico> listar();
}
