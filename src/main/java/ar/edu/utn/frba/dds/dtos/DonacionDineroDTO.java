package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import io.javalin.http.Context;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DonacionDineroDTO implements DTO {
  private Long id;
  private float monto;
  private int frecuencia;
  private String unidadFrecuencia;
  private String fecha;
  private Long personaHumanaId;
  private Long personaJuridicaId;

  public DonacionDineroDTO(DonacionDinero donacion) {
    this.id = donacion.getId();
    this.monto = donacion.getMonto();
    this.frecuencia = donacion.getFrecuencia();
    this.unidadFrecuencia = donacion.getUnidadFrecuencia();
    this.fecha = donacion.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    this.personaHumanaId = donacion.getPersonaHumana() != null ? donacion.getPersonaHumana().getId() : null;
    this.personaJuridicaId = donacion.getPersonaJuridica() != null ? donacion.getPersonaJuridica().getId() : null;
  }

  @Override
  public void obtenerFormulario(Context context) {
    this.monto = Float.parseFloat(context.formParam("monto"));
    this.frecuencia = Integer.parseInt(context.formParam("frecuencia"));
    this.unidadFrecuencia = context.formParam("unidadFrecuencia");
    this.fecha = context.formParam("fecha");
    this.personaHumanaId = context.formParam("personaHumanaId") != null ? Long.parseLong(context.formParam("personaHumanaId")) : null;
    this.personaJuridicaId = context.formParam("personaJuridicaId") != null ? Long.parseLong(context.formParam("personaJuridicaId")) : null;
  }

  @Override
  public boolean equals(Object obj) {
    DonacionDineroDTO that = (DonacionDineroDTO) obj;
    return Objects.equals(monto, that.monto) &&
        Objects.equals(frecuencia, that.frecuencia) &&
        Objects.equals(unidadFrecuencia, that.unidadFrecuencia) &&
        Objects.equals(fecha, that.fecha) &&
        Objects.equals(personaHumanaId, that.personaHumanaId) &&
        Objects.equals(personaJuridicaId, that.personaJuridicaId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(monto, frecuencia, unidadFrecuencia, fecha, personaHumanaId, personaJuridicaId);
  }
}
