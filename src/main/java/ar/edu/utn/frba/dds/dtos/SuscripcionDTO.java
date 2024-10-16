package ar.edu.utn.frba.dds.dtos;
import ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones.Suscripcion;
import io.javalin.http.Context;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
;

import java.util.Objects;
@Getter @Setter
@Data
@NoArgsConstructor
public class SuscripcionDTO implements DTO {
    private String tipoSuscripcion;
    private Long id; // El ID de la suscripción
    private Integer cantidadViandasFaltantes;
    private Integer cantidadViandasQueQuedan;
    private Long idHeladera; // ID de la heladera asociada
    private Long idPersonaHumana; // ID del suscriptor (persona humana)

    // Constructor que toma una entidad Suscripcion
    public SuscripcionDTO(Suscripcion suscripcion,String tipoSuscripcion) {
        this.tipoSuscripcion = tipoSuscripcion;
        this.id = suscripcion.getId();
        this.cantidadViandasFaltantes = suscripcion.getHeladera().getCapacidadMaximaViandas() - suscripcion.getHeladera().cantidadViandasVirtuales();
        this.cantidadViandasQueQuedan = suscripcion.getHeladera().cantidadViandas();
        this.idHeladera = suscripcion.getHeladera().getId();
        this.idPersonaHumana = suscripcion.getSuscriptor().getId();
    }

    // Método para obtener datos del formulario desde el contexto de Javalin
    @Override
    public void obtenerFormulario(Context context) {
        this.tipoSuscripcion = context.formParam("tipoSuscripcion");
        this.idHeladera = Long.parseLong(Objects.requireNonNull(context.formParam("heladeraId")));
        this.idPersonaHumana = Long.parseLong(Objects.requireNonNull(context.formParam("personaId")));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SuscripcionDTO that = (SuscripcionDTO) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(cantidadViandasFaltantes, that.cantidadViandasFaltantes) &&
                Objects.equals(cantidadViandasQueQuedan, that.cantidadViandasQueQuedan) &&
                Objects.equals(idHeladera, that.idHeladera) &&
                Objects.equals(idPersonaHumana, that.idPersonaHumana);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cantidadViandasFaltantes, cantidadViandasQueQuedan, idHeladera, idPersonaHumana);
    }
}