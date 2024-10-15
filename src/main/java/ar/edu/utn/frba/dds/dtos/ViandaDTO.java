package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import io.javalin.http.Context;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViandaDTO implements DTO {
  private Long id;
  private String fechaCaducidad;
  private boolean entregada;
  private String comida;
  private float caloriasEnKcal;
  private float pesoEnGramos;
  private String fechaDonacion;
  private Long personaHumanaId;

  public ViandaDTO(Vianda vianda) {
    this.id = vianda.getId();
    this.fechaCaducidad = vianda.getFechaCaducidad().toString();
    this.entregada = vianda.isEntregada();
    this.comida = vianda.getComida();
    this.caloriasEnKcal = vianda.getCaloriasEnKcal();
    this.pesoEnGramos = vianda.getPesoEnGramos();
    this.fechaDonacion = vianda.getFechaDonacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    this.personaHumanaId = vianda.getPersonaHumana() != null ? vianda.getPersonaHumana().getId() : null;
  }

  @Override
  public void obtenerFormulario(Context context) {
    this.fechaCaducidad = context.formParam("fechaCaducidad");
    this.entregada = false;
    this.comida = context.formParam("comida");
    this.caloriasEnKcal = Float.parseFloat(context.formParam("caloriasEnKcal"));
    this.pesoEnGramos = Float.parseFloat(context.formParam("pesoEnGramos"));
    this.fechaDonacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    this.personaHumanaId = Long.parseLong(context.sessionAttribute("idUsuario"));
  }
}

