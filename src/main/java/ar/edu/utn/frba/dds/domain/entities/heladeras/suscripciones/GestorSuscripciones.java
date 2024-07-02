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
  private Map<TipoSuscripcion, Map<Integer, List<Suscripcion>>> suscripcionesPorTipo;
  @Getter @Setter
  private Heladera heladera;

  public GestorSuscripciones() {
    this.suscripcionesPorTipo = new HashMap<>();
  }

  public void notificar(int cantidadViandas, TipoSuscripcion tipo) {
    List<Suscripcion> suscripciones = switch (tipo) {
        case DESPERFECTO -> suscripcionesPorTipo.get(tipo).get(0);
        case FALTAN_N_VIANDAS, QUEDAN_N_VIANDAS -> suscripcionesPorTipo.get(tipo).get(cantidadViandas);
        default -> throw new TipoSuscripcionInvalidoException();
    };

    suscripciones.forEach(suscripcion -> suscripcion.notificar(this.heladera));
  }

  public void suscribirPersona(PersonaHumana persona, TipoSuscripcion tipoSuscripcion, int cantidadViandas) {
    if (!suscripcionValida(persona)) throw new SuscripcionNoCercanaException();

    // FIXME: Algun dia esto no deberia un ser un switch y usariamos las etiquetas
    Suscripcion suscripcion;
    switch (tipoSuscripcion) {
      case QUEDAN_N_VIANDAS -> suscripcion = new QuedanNViandas(persona, cantidadViandas);
      case FALTAN_N_VIANDAS -> suscripcion = new FaltanNViandas(persona, cantidadViandas);
      default -> throw new TipoSuscripcionInvalidoException();
    }

    List<Suscripcion> listaSuscripciones = suscripcionesPorTipo.get(tipoSuscripcion).get(cantidadViandas);
    listaSuscripciones.add(suscripcion);
    this.suscripcionesPorTipo.get(tipoSuscripcion).put(cantidadViandas, listaSuscripciones);
  }

  public void suscribirPersona(PersonaHumana persona)  {
    if (!suscripcionValida(persona)) throw new SuscripcionNoCercanaException();

    Desperfecto desperfecto = new Desperfecto(persona);
    List<Suscripcion> listaSuscripciones = suscripcionesPorTipo.get(TipoSuscripcion.DESPERFECTO).get(0);
    listaSuscripciones.add(desperfecto);
    this.suscripcionesPorTipo.get(TipoSuscripcion.DESPERFECTO).put(0, listaSuscripciones);
  }

  public boolean suscripcionValida(PersonaHumana persona) {
    return persona.getDireccion().getMunicipio().equals(heladera.getDireccion().getMunicipio())
            && persona.getDireccion().getProvincia().equals(heladera.getDireccion().getProvincia());
  }

  public static Object instanciarTipoSuscripcion(String className, PersonaHumana persona, int cantidadViandas) throws Exception {
    Class<?> clazz = Class.forName(className);
    return clazz.getDeclaredConstructor().newInstance(persona, cantidadViandas);
  }
}

