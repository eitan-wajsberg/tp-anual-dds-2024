package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import io.javalin.http.Context;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
@NoArgsConstructor
public class DonacionViandaDTO implements DTO {
  private Long id;
  private String comida;
  private float caloriasEnKcal;
  private float pesoEnGramos;
  private String fechaCaducidad;
  private boolean entregada;
  private String fechaDonacion;
  private Long personaHumanaId;

  public DonacionViandaDTO(Vianda vianda) {
    this.id = vianda.getId();
    this.comida = vianda.getComida();
    this.caloriasEnKcal = vianda.getCaloriasEnKcal();
    this.pesoEnGramos = vianda.getPesoEnGramos();
    this.fechaCaducidad = vianda.getFechaCaducidad() != null ? vianda.getFechaCaducidad().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    this.entregada = vianda.isEntregada();
    this.fechaDonacion = vianda.getFechaDonacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    this.personaHumanaId = vianda.getPersonaHumana() != null ? vianda.getPersonaHumana().getId() : null;
  }

  @Override
  public void obtenerFormulario(Context context) {
    this.comida = context.formParam("comida");
    this.caloriasEnKcal = Float.parseFloat(context.formParam("caloriasEnKcal"));
    this.pesoEnGramos = Float.parseFloat(context.formParam("pesoEnGramos"));
    this.fechaCaducidad = context.formParam("fechaCaducidad");
    this.entregada = Boolean.parseBoolean(context.formParam("entregada"));
    this.fechaDonacion = context.formParam("fechaDonacion");
    this.personaHumanaId = Long.parseLong(context.formParam("personaHumanaId"));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    DonacionViandaDTO that = (DonacionViandaDTO) obj;
    return Float.compare(that.caloriasEnKcal, caloriasEnKcal) == 0 &&
        Float.compare(that.pesoEnGramos, pesoEnGramos) == 0 &&
        entregada == that.entregada &&
        Objects.equals(comida, that.comida) &&
        Objects.equals(fechaCaducidad, that.fechaCaducidad) &&
        Objects.equals(fechaDonacion, that.fechaDonacion) &&
        Objects.equals(personaHumanaId, that.personaHumanaId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comida, caloriasEnKcal, pesoEnGramos, fechaCaducidad, entregada, fechaDonacion, personaHumanaId);
  }
}
