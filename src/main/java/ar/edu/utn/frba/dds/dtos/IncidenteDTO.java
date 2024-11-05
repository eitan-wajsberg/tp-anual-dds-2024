package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.Incidente;
import io.javalin.http.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
public class IncidenteDTO implements DTO {
  private Long id;
  private Long heladeraId;
  private Long colaboradorId;
  private String tipoIncidente;
  private String tipoAlerta;
  private String descripcionDelColaborador;

  public IncidenteDTO(Incidente incidente) {
    this.id = incidente.getId();
    this.heladeraId = incidente.getHeladera().getId();
    this.colaboradorId = incidente.getColaborador().getId();
    this.tipoIncidente = incidente.getTipoIncidente().getClass().getSimpleName();
    this.tipoAlerta = incidente.getTipoAlerta() != null ? incidente.getTipoAlerta().name() : null;
    this.descripcionDelColaborador = incidente.getDescripcionDelColaborador();
  }

  @Override
  public void obtenerFormulario(Context context) {
    this.heladeraId = Long.parseLong(context.pathParam("heladeraId"));
    this.tipoIncidente = context.formParam("tipoFalla");
    this.colaboradorId = context.sessionAttribute("id");
    this.descripcionDelColaborador = context.formParam("descripcion");
  }

  @Override
  public boolean equals(Object obj) {
    IncidenteDTO that = (IncidenteDTO) obj;
    return Objects.equals(heladeraId, that.heladeraId) &&
        Objects.equals(colaboradorId, that.colaboradorId) &&
        Objects.equals(tipoIncidente, that.tipoIncidente) &&
        Objects.equals(tipoAlerta, that.tipoAlerta) &&
        Objects.equals(descripcionDelColaborador, that.descripcionDelColaborador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(heladeraId, colaboradorId, tipoIncidente, tipoAlerta, descripcionDelColaborador);
  }
}