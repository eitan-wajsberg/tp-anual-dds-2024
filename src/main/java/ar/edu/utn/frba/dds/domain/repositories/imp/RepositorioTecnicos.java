package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.repositories.IRepositorioTecnicos;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioTecnicos implements IRepositorioTecnicos {
    private final List<Tecnico> tecnicos;
    public RepositorioTecnicos() { this.tecnicos = new ArrayList<>(); }

    @Override
    public Long guardar(Tecnico tecnico) {
      return null;
    }
    @Override
    public List<Tecnico> listar() { return this.tecnicos; }
}
