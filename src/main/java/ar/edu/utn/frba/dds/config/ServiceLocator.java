package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
import ar.edu.utn.frba.dds.controllers.ControladorDistribucionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionDinero;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionVianda;
import ar.edu.utn.frba.dds.controllers.ControladorEleccionTipoCuenta;
import ar.edu.utn.frba.dds.controllers.ControladorInicio;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaHumana;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaJuridica;
import ar.edu.utn.frba.dds.controllers.ControladorPersonaVulnerable;
import ar.edu.utn.frba.dds.controllers.ControladorRegistroUsuario;
import ar.edu.utn.frba.dds.controllers.ControladorReportes;
import ar.edu.utn.frba.dds.controllers.ControladorTecnicos;
import ar.edu.utn.frba.dds.domain.adapters.AdaptadaJavaXMail;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDistribucionVianda;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionDinero;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionVianda;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaJuridica;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRol;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioUsuario;
import ar.edu.utn.frba.dds.services.IPersonaHumanaServices;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static Map<String, Object> instances = new HashMap<>();

  @SuppressWarnings("unchecked")
  public static <T> T instanceOf(Class<T> componentClass) {
    String componentName = componentClass.getName();
    if (!instances.containsKey(componentName)) {
      Object instance = null;
      if (componentName.equals(ControladorPersonaVulnerable.class.getName())) {
        instance = new ControladorPersonaVulnerable(
            instanceOf(Repositorio.class),
            instanceOf(RepositorioPersonaHumana.class)
        );
      } else if (componentName.equals(ControladorRegistroUsuario.class.getName())) {
        instance = new ControladorRegistroUsuario(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioRol.class)
        );
      } else if (componentName.equals(ControladorEleccionTipoCuenta.class.getName())) {
        instance = new ControladorEleccionTipoCuenta();
      } else if (componentName.equals(ControladorTecnicos.class.getName())) {
        instance = new ControladorTecnicos(instanceOf(RepositorioTecnicos.class));
      } else if (componentName.equals(ControladorCargaMasiva.class.getName())) {
        instance = new ControladorCargaMasiva(
            ServiceLocator.instanceOf(RepositorioPersonaHumana.class),
            ServiceLocator.instanceOf(AdaptadaJavaXMail.class),
            ServiceLocator.instanceOf(RepositorioUsuario.class),
            ServiceLocator.instanceOf(RepositorioRol.class),
            ServiceLocator.instanceOf(Repositorio.class)
        );
      } else if (componentName.equals(ControladorInicio.class.getName())) {
        instance = new ControladorInicio();
      } else if (componentName.equals(ControladorReportes.class.getName())) {
        instance = new ControladorReportes();
      } else if (componentName.equals(ControladorPersonaHumana.class.getName())) {
        instance = new ControladorPersonaHumana(
            instanceOf(IPersonaHumanaServices.class),
            instanceOf(RepositorioPersonaHumana.class));
      } else if (componentName.equals(ControladorPersonaJuridica.class.getName())) {
        instance = new ControladorPersonaJuridica(instanceOf(RepositorioPersonaJuridica.class));
      } else if (componentName.equals(ControladorDonacionVianda.class.getName())) {
        instance = new ControladorDonacionVianda(
            instanceOf(RepositorioDonacionVianda.class),
            instanceOf(RepositorioPersonaHumana.class));
      } else if (componentName.equals(ControladorDonacionDinero.class.getName())) {
        instance = new ControladorDonacionDinero(
            instanceOf(RepositorioDonacionDinero.class),
            instanceOf(RepositorioPersonaHumana.class),
            instanceOf(RepositorioPersonaJuridica.class));
      } else if (componentName.equals(Repositorio.class.getName())) {
        instance = new Repositorio();
      } else if (componentName.equals(RepositorioPersonaHumana.class.getName())) {
        instance = new RepositorioPersonaHumana();
      } else if (componentName.equals(RepositorioPersonaJuridica.class.getName())) {
        instance = new RepositorioPersonaJuridica();
      } else if (componentName.equals(RepositorioDonacionDinero.class.getName())) {
        instance = new RepositorioDonacionDinero();
      } else if (componentName.equals(RepositorioDonacionVianda.class.getName())) {
        instance = new RepositorioDonacionVianda();
      } else if (componentName.equals(RepositorioTecnicos.class.getName())) {
        instance = new RepositorioTecnicos();
      } else if (componentName.equals(RepositorioUsuario.class.getName())) {
        instance = new RepositorioUsuario();
      } else if (componentName.equals(RepositorioRol.class.getName())) {
        instance = new RepositorioRol();
      } else if (componentName.equals(AdaptadaJavaXMail.class.getName())) {
        instance = new AdaptadaJavaXMail();
      } else if (componentName.equals(ControladorDistribucionVianda.class.getName())) {
        instance = new ControladorDistribucionVianda(instanceOf(RepositorioDistribucionVianda.class));
      } else if (componentName.equals(RepositorioDistribucionVianda.class.getName())) {
        instance = new RepositorioDistribucionVianda();
      }
      instances.put(componentName, instance);
    }

    return (T) instances.get(componentName);
  }
}