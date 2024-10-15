package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.UnidadFrecuencia;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.dtos.DonacionDineroDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
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
  //TODO: Is it necessary?
  }

  public void create(Context context) {
    context.render(rutaDonacionHbs);
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

      DonacionDinero nuevaDonacion = new DonacionDinero();
      nuevaDonacion.setMonto(dto.getMonto());
      nuevaDonacion.setUnidadFrecuencia(UnidadFrecuencia.valueOf(dto.getUnidadFrecuencia().toUpperCase()));
      nuevaDonacion.setFecha(LocalDate.now());

      personaHumana.ifPresent(nuevaDonacion::setPersonaHumana);
      personaJuridica.ifPresent(nuevaDonacion::setPersonaJuridica);

      withTransaction(() -> repositorioGenerico.guardar(nuevaDonacion));
      context.redirect(rutaListadoDonaciones);
    } catch (Exception e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", e.getMessage());
      model.put("dto", dto);
      context.render(rutaDonacionHbs, model);
    }
  }

  //TODO: Si selecciono una donacion en pantalla que devuelve la lista de donaciones realizadas puedo modificar la misma
  @Override
  public void edit(Context context) {
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<DonacionDinero> donacion = repositorioGenerico.buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

      if (donacion.isEmpty()) {
        throw new RuntimeException("No existe la donación.");
      }

      DonacionDineroDTO dto = new DonacionDineroDTO(donacion.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaDonacionHbs, model);
    } catch (Exception e) {
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
      Optional<DonacionDinero> donacionExistente = repositorioGenerico.buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

      if (donacionExistente.isEmpty()) {
        throw new RuntimeException("Donación no encontrada.");
      }

      DonacionDinero donacion = donacionExistente.get();
      donacion.setMonto(dtoNuevo.getMonto());
      donacion.setUnidadFrecuencia(UnidadFrecuencia.valueOf(dtoNuevo.getUnidadFrecuencia().toUpperCase()));
      donacion.setFecha(LocalDate.parse(dtoNuevo.getFecha()));

      repositorioGenerico.actualizar(donacion);

      context.redirect("/donacionDinero");
    } catch (Exception e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
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
      withTransaction(() -> repositorioGenerico.eliminarFisico(DonacionDinero.class, id));
      context.redirect("/donacionDinero");
    } else {
      context.status(400).result("No se puede eliminar, la donación no fue encontrada.");
    }
  }
}
