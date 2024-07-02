package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.Map;

public class GestorSuscripciones {
  private Map<TipoSuscripcion, Map<Integer, List<Suscripcion>>> suscripcionesPorTipo;

  public void notificar(int cantidadViandas, TipoSuscripcion tipo, Heladera heladera) {
    List<Suscripcion> suscripciones = switch (tipo) {
        case DESPERFECTO -> suscripcionesPorTipo.get(tipo).get(0);
        case FALTAN_N_VIANDAS, QUEDAN_N_VIANDAS -> suscripcionesPorTipo.get(tipo).get(cantidadViandas);
        default -> throw new TipoSuscripcionInvalidoException();
    };

    suscripciones.forEach(suscripcion -> suscripcion.notificar(heladera));
  }

  public void suscribirPersona(PersonaHumana persona, TipoSuscripcion tipoSuscripcion, int cantidadViandas) {
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

  public void suscribirPersona(PersonaHumana persona) {
    Desperfecto desperfecto = new Desperfecto(persona);
    List<Suscripcion> listaSuscripciones = suscripcionesPorTipo.get(TipoSuscripcion.DESPERFECTO).get(0);
    listaSuscripciones.add(desperfecto);
    this.suscripcionesPorTipo.get(TipoSuscripcion.DESPERFECTO).put(0, listaSuscripciones);
  }

  public static Object instanciarTipoSuscripcion(String className, PersonaHumana persona, int cantidadViandas) throws Exception {
    Class<?> clazz = Class.forName(className);
    return clazz.getDeclaredConstructor().newInstance(persona, cantidadViandas);
  }
}

