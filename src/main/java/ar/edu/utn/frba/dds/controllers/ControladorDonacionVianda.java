package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionVianda;
import ar.edu.utn.frba.dds.dtos.DonacionViandaDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ControladorDonacionVianda implements ICrudViewsHandler {

  private final String rutaRegistroHbs = "colaboraciones/donacionVianda.hbs";
  private final String rutaListadoHbs = "colaboraciones/listadoDonacionesVianda.hbs";
  private Repositorio repositorioVianda;
  private Repositorio repositorioPersonaHumana;

  public ControladorDonacionVianda(Repositorio repositorioVianda, Repositorio repositorioPersonaHumana) {
    this.repositorioVianda = repositorioVianda;
    this.repositorioPersonaHumana = repositorioPersonaHumana;
  }

  @Override
  public void index(Context context) {
    List<Vianda> donaciones = repositorioVianda.buscarTodos(Vianda.class);

    Map<String, Object> model = new HashMap<>();
    model.put("donacionesVianda", donaciones);

    context.render(rutaListadoHbs, model);
  }

  @Override
  public void show(Context context) {
    // No specific show method needed.
  }

  @Override
  public void create(Context context) {
    context.render(rutaRegistroHbs);
  }

  @Override
  public void save(Context context) {
    DonacionViandaDTO dto = new DonacionViandaDTO();
    dto.obtenerFormulario(context);

    try {
      Optional<PersonaHumana> personaHumana = repositorioPersonaHumana.buscarPorId(dto.getPersonaHumanaId(), PersonaHumana.class);

      Vianda nuevaVianda = new Vianda();
      nuevaVianda.setComida(dto.getComida());
      nuevaVianda.setCaloriasEnKcal(dto.getCaloriasEnKcal());
      nuevaVianda.setPesoEnGramos(dto.getPesoEnGramos());
      nuevaVianda.setFechaDonacion(LocalDate.parse(dto.getFechaDonacion(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      nuevaVianda.setFechaCaducidad(dto.getFechaCaducidad() != null ? LocalDateTime.parse(dto.getFechaCaducidad(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
      nuevaVianda.setEntregada(dto.isEntregada());

      personaHumana.ifPresent(nuevaVianda::setPersonaHumana);

      repositorioVianda.guardar(nuevaVianda);

      context.redirect(rutaListadoHbs);

    } catch (Exception e) {
      Map<String, Object> model = new HashMap<>();
      model.put("error", "Error al procesar la donación de vianda.");
      model.put("dto", dto);
      context.render(rutaRegistroHbs, model);
    }
  }

  @Override
  public void edit(Context context) {
    /*
    Map<String, Object> model = new HashMap<>();
    try {
      Optional<Vianda> vianda = repositorioVianda.buscarPorId(Long.valueOf(context.pathParam("id")), Vianda.class);

      if (vianda.isEmpty()) {
        throw new RuntimeException("No existe la donación de vianda.");
      }

      DonacionViandaDTO dto = new DonacionViandaDTO(vianda.get());
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
    DonacionViandaDTO dtoNuevo = new DonacionViandaDTO();
    dtoNuevo.obtenerFormulario(context);

    try {
      Optional<Vianda> viandaExistente = repositorioVianda.buscarPorId(Long.valueOf(context.pathParam("id")), Vianda.class);

      if (viandaExistente.isEmpty()) {
        throw new RuntimeException("Donación de vianda no encontrada.");
      }

      Vianda vianda = viandaExistente.get();
      vianda.setComida(dtoNuevo.getComida());
      vianda.setCaloriasEnKcal(dtoNuevo.getCaloriasEnKcal());
      vianda.setPesoEnGramos(dtoNuevo.getPesoEnGramos());
      vianda.setFechaDonacion(LocalDate.parse(dtoNuevo.getFechaDonacion(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      vianda.setFechaCaducidad(dtoNuevo.getFechaCaducidad() != null ? LocalDateTime.parse(dtoNuevo.getFechaCaducidad(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
      vianda.setEntregada(dtoNuevo.isEntregada());

      repositorioVianda.actualizar(vianda);

      context.redirect(rutaListadoHbs);
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
    Optional<Vianda> vianda = repositorioVianda.buscarPorId(id, Vianda.class);

    if (vianda.isPresent()) {
      repositorioVianda.eliminarFisico(Vianda.class, id);
      context.redirect(rutaListadoHbs);
    } else {
      context.status(400).result("No se puede eliminar, la donación de vianda no fue encontrada.");
    }
    */
  }

}
