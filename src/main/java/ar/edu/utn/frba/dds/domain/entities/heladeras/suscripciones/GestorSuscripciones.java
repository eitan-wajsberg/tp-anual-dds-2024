package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.reportes.CantidadFallasPorHeladera;
import ar.edu.utn.frba.dds.domain.entities.reportes.GeneradorReportes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorSuscripciones {
  @Getter
  private Map<TipoSuscripcion, List<Suscripcion>> suscripcionesPorTipo;

  public GestorSuscripciones() {
    this.suscripcionesPorTipo = new HashMap<>();
  }

  public void notificar(TipoSuscripcion tipo, Heladera heladera) {
    List<Suscripcion> suscripciones = switch (tipo) {
        case DESPERFECTO -> suscripcionesPorTipo.get(tipo);
        case FALTAN_N_VIANDAS, QUEDAN_N_VIANDAS -> suscripcionesPorTipo.get(tipo);
    };

    suscripciones.forEach(suscripcion -> suscripcion.notificar(heladera));
  }
  
  public void agregarSuscripcionPorTipo(TipoSuscripcion tipo, Suscripcion suscripcion, Heladera heladera) {
    if (suscripcionValida(suscripcion.colaborador, heladera)) {
      throw new SuscripcionNoCercanaException();
    }

    List<Suscripcion> suscripciones = this.suscripcionesPorTipo.get(tipo);
    suscripciones.add(suscripcion);
    this.suscripcionesPorTipo.put(tipo, suscripciones);
  }

  public boolean suscripcionValida(PersonaHumana persona, Heladera heladera) {
    return persona.getDireccion().getMunicipio().equals(heladera.getDireccion().getMunicipio())
            && persona.getDireccion().getProvincia().equals(heladera.getDireccion().getProvincia());
  }
}