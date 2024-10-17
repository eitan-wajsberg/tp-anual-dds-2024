package ar.edu.utn.frba.dds.dtos;


import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.Incidente;
import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.TipoAlerta;
import ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import io.javalin.http.Context;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
        this.colaboradorId = Long.parseLong(context.pathParam("personaId"));
        this.tipoIncidente = context.formParam("tipoFalla");
        this.descripcionDelColaborador = context.formParam("descripcion");
    }

    public Incidente toEntity(Heladera heladera, PersonaHumana colaborador, TipoIncidente tipoIncidente, TipoAlerta tipoAlerta) {
        Incidente incidente = new Incidente();
        incidente.setHeladera(heladera);
        incidente.setColaborador(colaborador);
        incidente.setTipoIncidente(tipoIncidente);
        incidente.setTipoAlerta(tipoAlerta);
        incidente.setDescripcionDelColaborador(this.descripcionDelColaborador);
        incidente.setFecha(LocalDateTime.now());
        incidente.setSolucionado(false);
        return incidente;
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
