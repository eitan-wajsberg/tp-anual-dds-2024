package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaJuridica;

public class ControladorPersonaJuridica {

  private RepositorioPersonaJuridica repositorioPersonaJuridica;

  public ControladorPersonaJuridica(RepositorioPersonaJuridica repositorioPersonaJuridica) {
    this.repositorioPersonaJuridica = repositorioPersonaJuridica;
  }
}
