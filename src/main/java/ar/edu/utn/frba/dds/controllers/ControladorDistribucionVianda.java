package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.AccionApertura;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.SolicitudApertura;
import ar.edu.utn.frba.dds.domain.entities.viandas.DistribucionVianda;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDistribucionVianda;
import ar.edu.utn.frba.dds.dtos.DistribucionViandaOutputDTO;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import io.javalin.http.Context;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorDistribucionVianda implements ICrudViewsHandler {
  private RepositorioDistribucionVianda repositorioDistribucion;
  public ControladorDistribucionVianda(RepositorioDistribucionVianda repositorioDistribucion){ this.repositorioDistribucion = repositorioDistribucion; }
  @Override
  public void index(Context context) {
    Long colaboradorId = Long.valueOf("8");//context.sessionAttribute("id"); //TODO cambiar

    List<DistribucionVianda> distribuciones = this.repositorioDistribucion.buscarDistribuciones(colaboradorId);

    List<DistribucionViandaOutputDTO> distDTOs = distribuciones.stream().map(dist -> fromEntity(dist)).collect(Collectors.toList());

    Map<String, Object> model = new HashMap<>();
    model.put("distribuciones", distDTOs);

    context.render("colaboraciones/distribucionesDeViandas.hbs", model);
  }

  @Override
  public void show(Context context) {
    context.status(501);
  }

  @Override
  public void create(Context context) {
    context.status(501);
  }

  @Override
  public void save(Context context) {
    context.status(501);
  }

  @Override
  public void edit(Context context) {
    context.status(501);
    // busco distribucion x id en el repositorio

    // si no existe, etc...
    // si está finalizada, redigir a visualización. No se puede modificar (misma validación debería agregar al update)
  }

  @Override
  public void update(Context context) {
    context.status(501);
  }

  @Override
  public void delete(Context context) {
    context.status(501);
  }

  public static DistribucionVianda fromDTO(DistribucionViandaOutputDTO dto){
    return null;
  }

  public static DistribucionViandaOutputDTO fromEntity(DistribucionVianda entity){
    DistribucionViandaOutputDTO.DistribucionViandaOutputDTOBuilder dtoBuilder = DistribucionViandaOutputDTO.builder()
        .id(entity.getId())
        .fecha(entity.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yy")))
        .heladeraOrigenId(entity.getHeladeraOrigen().getId())
        .heladeraOrigenNombre(entity.getHeladeraOrigen().getNombre())
        .heladeraDestinoId(entity.getHeladeraDestino().getId())
        .heladeraDestinoNombre(entity.getHeladeraDestino().getNombre())
        .terminada(entity.isTerminada())
        .motivo(entity.getMotivo())
        .cantidadViandas(entity.getCantidadViandas());

    Optional<SolicitudApertura> solicitudOrigen = entity.getHeladeraOrigen()
        .getSolicitudesDeApertura().stream().filter(c-> !c.isAperturaConcretada()
            && c.getFechaSolicitud().isAfter(entity.getFecha().atStartOfDay())
            && c.getAccion().equals(AccionApertura.QUITAR_VIANDA)).findFirst();

    if(solicitudOrigen.isPresent()){
      dtoBuilder.horaSolicitudEnOrigen(solicitudOrigen.get().getFechaConcrecion().format(DateTimeFormatter. ofPattern("HH:mm:ss")));

      Optional<SolicitudApertura> solicitudDestino = entity.getHeladeraOrigen()
          .getSolicitudesDeApertura().stream().filter(c-> !c.isAperturaConcretada()
              && c.getFechaSolicitud().isAfter(solicitudOrigen.get().getFechaConcrecion())
              && c.getAccion().equals(AccionApertura.INGRESAR_VIANDA)).findFirst();

      if(solicitudDestino.isPresent()){
        dtoBuilder.horaSolicitudEnDestino(solicitudDestino.get().getFechaConcrecion().format(DateTimeFormatter. ofPattern("HH:mm:ss")));
      }
    }

    return dtoBuilder.build();
  }
}
