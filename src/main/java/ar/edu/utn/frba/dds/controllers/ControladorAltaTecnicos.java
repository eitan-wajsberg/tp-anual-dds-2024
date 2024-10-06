package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Rol;
import ar.edu.utn.frba.dds.domain.entities.usuarios.Usuario;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.Objects;
import java.util.Optional;

public class ControladorAltaTecnicos implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private RepositorioTecnicos repositorioTecnicos;
  private final String rutaHbs = PrettyProperties.getInstance().propertyFromName("hbs_alta_tecnicos");

  public ControladorAltaTecnicos(RepositorioTecnicos repositorioTecnico) {
    this.repositorioTecnicos = repositorioTecnico;
  }

  @Override
  public void index(Context context) {
    // TODO
  }

  @Override
  public void show(Context context) {
    // TODO
  }

  @Override
  public void create(Context context) {
    context.render(rutaHbs);
  }

  @Override
  public void save(Context context) {
    TecnicoDTO dto = new TecnicoDTO();
    dto.obtenerFormulario(context, rutaHbs);
    Tecnico nuevoTecnico = (Tecnico) dto.convertirAEntidad();

    if (nuevoTecnico == null) {
      throw new ValidacionFormularioException("Los datos del técnico son inválidos.", rutaHbs);
    }
    
    withTransaction(() -> repositorioTecnicos.guardar(nuevoTecnico));

    context.redirect("/admin");
  }

  @Override
  public void edit(Context context) {
    // TODO
  }

  @Override
  public void update(Context context) {
    // TODO
  }

  @Override
  public void delete(Context context) {
    // TODO
  }
}
