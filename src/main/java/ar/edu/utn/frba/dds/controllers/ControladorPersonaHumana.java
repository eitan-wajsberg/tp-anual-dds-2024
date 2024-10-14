package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.adapters.AdaptadaJavaXMail;
import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.dtos.inputs.personasHumanas.PersonaHumanaInputDTO;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;

public class ControladorPersonaHumana implements ICrudViewsHandler {

  private IPersonaHumanaServices personaHumanaServices;
  private RepositorioPersonaHumana repositorioPersonaHumana;

  public ControladorPersonaHumana(IPersonaHumanaServices personaHumanaServices, RepositorioPersonaHumana repositorioPersonaHumana) {
    this.personaHumanaServices = personaHumanaServices;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }


  public void descubrirPersonaHumana(Object data, AdaptadaJavaXMail mailSender){
    PersonaHumanaInputDTO dto = (PersonaHumanaInputDTO) data;

    // TODO obtener usuario
    Usuario usuarioActual = null;

    personaHumanaServices.descubrirPersonaHumana(dto, usuarioActual, mailSender);
  }

  public void agregarColaboracion(Usuario usuario, PersonaHumanaInputDTO data, Contribucion contribucion){}

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
