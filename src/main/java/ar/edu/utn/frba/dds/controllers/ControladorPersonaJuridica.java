package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaJuridica;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;

public class ControladorPersonaJuridica implements ICrudViewsHandler {

  private RepositorioPersonaJuridica repositorioPersonaJuridica;

  public ControladorPersonaJuridica(RepositorioPersonaJuridica repositorioPersonaJuridica) {
    this.repositorioPersonaJuridica = repositorioPersonaJuridica;
  }

  @Override
  public void index(Context context) {

  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }
}
