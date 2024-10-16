package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.UnidadFrecuencia;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.dtos.DonacionDineroDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorDonacionDinero implements ICrudViewsHandler, WithSimplePersistenceUnit {
  private Repositorio repositorioGenerico;
  private final String rutaListadoHbs = "colaboraciones/listadoDonacionesDinero.hbs";
  private final String rutaDonacionHbs = "colaboraciones/donacionDinero.hbs";
  private final String rutaListadoDonaciones = "/donacionDinero";
  private final String ERROR = "error";

  public ControladorDonacionDinero(Repositorio repositorioGenerico) {
    this.repositorioGenerico = repositorioGenerico;
  }

  @Override
  public void index(Context context) {
    List<DonacionDinero> donacionesDeDinero = this.repositorioGenerico.buscarTodos(DonacionDinero.class);
    Map<String, Object> model = new HashMap<>();
    model.put("donacionDinero", donacionesDeDinero);
    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    /*...*/
  }

  public void create(Context context) {
    Map<String, Object> model = new HashMap<>();
    model.put("title", "Donar dinero");
    context.render(rutaDonacionHbs, model);
  }

  @Override
  public void save(Context context) {
    DonacionDineroDTO dto = new DonacionDineroDTO();
    dto.obtenerFormulario(context);

    try {
      Optional<PersonaHumana> personaHumana = repositorioGenerico
          .buscarPorId(1L, PersonaHumana.class);
      Optional<PersonaJuridica> personaJuridica = repositorioGenerico
          .buscarPorId(dto.getPersonaJuridicaId(), PersonaJuridica.class);

      /*
      if (personaHumana.isEmpty() || personaJuridica.isEmpty()) {
        throw new ValidacionFormularioException("No se ha encontrado el id del usuario. Error en servidor.");
      }
      */

      DonacionDinero nuevaDonacion = DonacionDinero.fromDTO(dto);
      if (nuevaDonacion == null) {
        throw new ValidacionFormularioException("Se ha ingresado información incorrecta sobre la donación.");
      }

      personaHumana.ifPresent(nuevaDonacion::setPersonaHumana);
      personaJuridica.ifPresent(nuevaDonacion::setPersonaJuridica);

      if (personaHumana.isPresent()) {
        personaHumana.get().sumarPuntaje(nuevaDonacion.calcularPuntaje());
      }

      withTransaction(() -> {
        repositorioGenerico.guardar(nuevaDonacion);
        repositorioGenerico.actualizar(personaHumana.get());
      });
      context.redirect(rutaListadoDonaciones);

    } catch (ValidacionFormularioException e) {
      Map<String, Object> model = new HashMap<>();
      model.put(ERROR, e.getMessage());
      model.put("dto", dto);
      context.render(rutaDonacionHbs, model);
    }
  }

   @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<DonacionDinero> donacion = repositorioGenerico
          .buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

      if (donacion.isEmpty()) {
        throw new ValidacionFormularioException("No existe la donación de dinero.");
      }

      DonacionDineroDTO dto = new DonacionDineroDTO(donacion.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      model.put("title", "Editar donacion de dinero");
      context.render(rutaDonacionHbs, model);
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      context.render(rutaListadoHbs, model);
    }
  }

  @Override
  public void update(Context context) {
    Map<String, Object> model = new HashMap<>();
    DonacionDineroDTO dtoNuevo = new DonacionDineroDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<DonacionDinero> donacionExistente = repositorioGenerico
          .buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

      if (donacionExistente.isEmpty()) {
        throw new ValidacionFormularioException("Donación no encontrada.");
      }

      DonacionDineroDTO dtoExistente = new DonacionDineroDTO(donacionExistente.get());
      if (dtoExistente.equals(dtoNuevo)) {
        throw new ValidacionFormularioException("No se detectaron cambios en el formulario.");
      }

      donacionExistente.get().actualizarFromDto(dtoNuevo);
      withTransaction(() -> repositorioGenerico.actualizar(donacionExistente.get()));
      context.redirect(rutaListadoDonaciones);
    } catch (ValidacionFormularioException e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("title", "Editar donacion de dinero");
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaDonacionHbs, model);
    }

  }

  @Override
  public void delete(Context context) {
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<DonacionDinero> donacion = repositorioGenerico.buscarPorId(id, DonacionDinero.class);

    if (donacion.isPresent()) {
      withTransaction(() -> this.repositorioGenerico.eliminarFisico(DonacionDinero.class, id));
      context.redirect(rutaListadoDonaciones);
    } else {
      context.status(400).result("No se pudo cancelar correctamente la donación.");
    }
  }
}
