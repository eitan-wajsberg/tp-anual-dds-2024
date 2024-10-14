package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionDinero;
import ar.edu.utn.frba.dds.dtos.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.PersonaVulnerableDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorDonacionDinero implements ICrudViewsHandler {

  private Repositorio repositorioDonacionDinero;
  private Repositorio repositorioPersonaHumana;
  private Repositorio repositorioPersonaJuridica;

  private final String rutaListadoHbs = "colaboraciones/listadoDonacionesDinero.hbs";
  private final String rutaRegistroHbs = "colaboraciones/donacionDinero.hbs";
  //private final String rutaDetalleDonacion = "colaboracion/detalleDonacionVianda.hbs"

  public ControladorDonacionDinero(Repositorio repositorioDonacionDinero,
                                   Repositorio repositorioPersonaHumana,
                                   Repositorio repositorioPersonaJuridica) {
    this.repositorioDonacionDinero = repositorioDonacionDinero;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
    this.repositorioPersonaJuridica = repositorioPersonaJuridica;
  }

  @Override
  public void index(Context context){
    List<DonacionDinero> donacionesDeDinero = this.repositorioDonacionDinero.buscarTodos(DonacionDinero.class);

    Map<String, Object> model = new HashMap<>();
    model.put("donacionDinero", donacionesDeDinero);
    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
  //TODO: Is it necessary?
  }

  public void create(Context context){
    context.render(rutaRegistroHbs);
  }

  @Override
  public void save(Context context) {
    DonacionDineroDTO dto = new DonacionDineroDTO();
    dto.obtenerFormulario(context);

    try {
      Optional<PersonaHumana> personaHumana = repositorioPersonaHumana
          .buscarPorId(dto.getPersonaHumanaId(), PersonaHumana.class);
      Optional<PersonaJuridica> personaJuridica = repositorioPersonaJuridica
          .buscarPorId(dto.getPersonaJuridicaId(), PersonaJuridica.class);

      DonacionDinero nuevaDonacion = new DonacionDinero();
      nuevaDonacion.setMonto(dto.getMonto());
      nuevaDonacion.setFrecuencia(dto.getFrecuencia());
      nuevaDonacion.setUnidadFrecuencia(dto.getUnidadFrecuencia());
      nuevaDonacion.setFecha(LocalDate.parse(dto.getFecha()));

      personaHumana.ifPresent(nuevaDonacion::setPersonaHumana);
      personaJuridica.ifPresent(nuevaDonacion::setPersonaJuridica);

      repositorioDonacionDinero.guardar(nuevaDonacion);

      context.redirect("/donacionesDinero");
    } catch (Exception e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Error al procesar la donación.");
      model.put("dto", dto);
      context.render(rutaRegistroHbs, model);
    }

  }

  @Override
  public void edit(Context context) {
    /*
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<DonacionDinero> donacion = repositorioDonacionDinero.buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

      if (donacion.isEmpty()) {
        throw new RuntimeException("No existe la donación.");
      }

      DonacionDineroDTO dto = new DonacionDineroDTO(donacion.get());
      model.put("dto", dto);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaRegistroHbs, model);
    } catch (Exception e) {
      model.put("error", e.getMessage());
      context.render(rutaListadoHbs, model);
    }
    */
  }

  @Override
  public void update(Context context) {
    /*
    Map<String, Object> model = new HashMap<>();
    DonacionDineroDTO dtoNuevo = new DonacionDineroDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<DonacionDinero> donacionExistente = repositorioDonacionDinero.buscarPorId(Long.valueOf(context.pathParam("id")), DonacionDinero.class);

      if (donacionExistente.isEmpty()) {
        throw new RuntimeException("Donación no encontrada.");
      }

      DonacionDinero donacion = donacionExistente.get();
      donacion.setMonto(dtoNuevo.getMonto());
      donacion.setFrecuencia(dtoNuevo.getFrecuencia());
      donacion.setUnidadFrecuencia(dtoNuevo.getUnidadFrecuencia());
      donacion.setFecha(LocalDate.parse(dtoNuevo.getFecha()));

      repositorioDonacionDinero.actualizar(donacion);

      context.redirect("/donacionesDinero");
    } catch (Exception e) {
      model.put("error", e.getMessage());
      model.put("dto", dtoNuevo);
      model.put("edicion", true);
      model.put("id", context.pathParam("id"));
      context.render(rutaRegistroHbs, model);
    }
    */
  }

  @Override
  public void delete(Context context) {
    /*
    Long id = Long.valueOf(context.pathParam("id"));
    Optional<DonacionDinero> donacion = repositorioDonacionDinero.buscarPorId(id, DonacionDinero.class);

    if (donacion.isPresent()) {
      repositorioDonacionDinero.eliminarFisico(DonacionDinero.class, id);
      context.redirect("/donacionesDinero");
    } else {
      context.status(400).result("No se puede eliminar, la donación no fue encontrada.");
    }
    */
  }

}
