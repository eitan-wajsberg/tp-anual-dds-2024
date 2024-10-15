package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.domain.adapters.AdaptadaJavaXMail;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.*;

import java.util.HashMap;
import java.util.Map;

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
        ControladorRegistroUsuario instance = new ControladorRegistroUsuario(instanceOf(RepositorioUsuario.class), instanceOf(RepositorioRol.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorEleccionTipoCuenta.class.getName())) {
        ControladorEleccionTipoCuenta instance = new ControladorEleccionTipoCuenta();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorTecnicos.class.getName())) {
        ControladorTecnicos instance = new ControladorTecnicos(instanceOf(RepositorioTecnicos.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorCargaMasiva.class.getName())) {
        ControladorCargaMasiva instance = new ControladorCargaMasiva(ServiceLocator.instanceOf(RepositorioPersonaHumana.class), ServiceLocator.instanceOf(AdaptadaJavaXMail.class), ServiceLocator.instanceOf(Repositorio.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorInicio.class.getName())) {
        ControladorInicio instance = new ControladorInicio();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorReportes.class.getName())) {
        ControladorReportes instance = new ControladorReportes();
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
      } else if (componentName.equals(AdaptadaJavaXMail.class.getName())) {
        AdaptadaJavaXMail instance = new AdaptadaJavaXMail();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorMapaHeladeras.class.getName())) {
        ControladorMapaHeladeras instance = new ControladorMapaHeladeras(instanceOf(RepositorioHeladera.class), instanceOf(Repositorio.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioHeladera.class.getName())) {
        RepositorioHeladera instance = new RepositorioHeladera();
        instances.put(componentName, instance);
      }
    }

    return (T) instances.get(componentName);
  }
}