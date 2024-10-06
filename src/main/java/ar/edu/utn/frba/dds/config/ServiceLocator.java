package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.ControladorAltaTecnicos;
import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorInicio;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import ar.edu.utn.frba.dds.domain.entities.validador.AusenciaDeCredencialesPorDefecto;
import ar.edu.utn.frba.dds.domain.entities.validador.ListaDePeoresClavesMemorizadas;
import ar.edu.utn.frba.dds.domain.entities.validador.LongitudEstipulada;
import ar.edu.utn.frba.dds.domain.entities.validador.TipoValidacion;
import ar.edu.utn.frba.dds.domain.entities.validador.ValidadorDeClave;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRol;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioUsuario;
import ar.edu.utn.frba.dds.middlewares.AuthMiddleware;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServiceLocator {
  private static Map<String, Object> instances = new HashMap<>();

  @SuppressWarnings("unchecked")
  public static <T> T instanceOf(Class<T> componentClass) {
    String componentName = componentClass.getName();

    if (!instances.containsKey(componentName)) {
      if (componentName.equals(ControladorPersonaVulnerable.class.getName())) {
        ControladorPersonaVulnerable instance = new ControladorPersonaVulnerable(instanceOf(Repositorio.class), instanceOf(RepositorioPersonaHumana.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorRegistroUsuario.class.getName())) {
        ControladorRegistroUsuario instance = new ControladorRegistroUsuario(instanceOf(RepositorioUsuario.class), instanceOf(Repositorio.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorEleccionTipoCuenta.class.getName())) {
        ControladorEleccionTipoCuenta instance = new ControladorEleccionTipoCuenta();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorAltaTecnicos.class.getName())) {
        ControladorAltaTecnicos instance = new ControladorAltaTecnicos(instanceOf(RepositorioTecnicos.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorCargaMasiva.class.getName())) {
        ControladorCargaMasiva instance = new ControladorCargaMasiva();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorInicio.class.getName())) {
        ControladorInicio instance = new ControladorInicio();
        instances.put(componentName, instance);
      } else if (componentName.equals(Repositorio.class.getName())) {
        Repositorio instance = new Repositorio();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioPersonaHumana.class.getName())) {
        RepositorioPersonaHumana instance = new RepositorioPersonaHumana();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioTecnicos.class.getName())) {
        RepositorioTecnicos instance = new RepositorioTecnicos();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioUsuario.class.getName())) {
        RepositorioUsuario instance = new RepositorioUsuario();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioRol.class.getName())) {
        RepositorioRol instance = new RepositorioRol();
        instances.put(componentName, instance);
      }
    }

    return (T) instances.get(componentName);
  }
}