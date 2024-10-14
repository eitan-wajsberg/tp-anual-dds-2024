package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.ControladorCargaMasiva;
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
      if (componentName.equals(ControladorPersonaVulnerable.class.getName())) {
        ControladorPersonaVulnerable instance = new ControladorPersonaVulnerable(
            instanceOf(Repositorio.class),
            instanceOf(RepositorioPersonaHumana.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorRegistroUsuario.class.getName())) {
        ControladorRegistroUsuario instance = new ControladorRegistroUsuario(
            instanceOf(RepositorioUsuario.class),
            instanceOf(RepositorioRol.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorEleccionTipoCuenta.class.getName())) {
        ControladorEleccionTipoCuenta instance = new ControladorEleccionTipoCuenta();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorTecnicos.class.getName())) {
        ControladorTecnicos instance = new ControladorTecnicos(instanceOf(RepositorioTecnicos.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorCargaMasiva.class.getName())) {
        ControladorCargaMasiva instance = new ControladorCargaMasiva(
            ServiceLocator.instanceOf(RepositorioPersonaHumana.class),
            ServiceLocator.instanceOf(AdaptadaJavaXMail.class),
            ServiceLocator.instanceOf(RepositorioUsuario.class),
            ServiceLocator.instanceOf(RepositorioRol.class),
            ServiceLocator.instanceOf(Repositorio.class)
        );
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorInicio.class.getName())) {
        ControladorInicio instance = new ControladorInicio();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorReportes.class.getName())) {
        ControladorReportes instance = new ControladorReportes();
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorPersonaHumana.class.getName())) {
        ControladorPersonaHumana instance = new ControladorPersonaHumana(
            instanceOf(IPersonaHumanaServices.class),
            instanceOf(RepositorioPersonaHumana.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorPersonaJuridica.class.getName())) {
        ControladorPersonaJuridica instance = new ControladorPersonaJuridica(instanceOf(RepositorioPersonaJuridica.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorDonacionVianda.class.getName())) {
        ControladorDonacionVianda instance = new ControladorDonacionVianda(instanceOf(RepositorioDonacionVianda.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(ControladorDonacionDinero.class.getName())) {
        ControladorDonacionDinero instance = new ControladorDonacionDinero(instanceOf(RepositorioDonacionDinero.class));
        instances.put(componentName, instance);
      } else if (componentName.equals(Repositorio.class.getName())) {
        Repositorio instance = new Repositorio();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioPersonaHumana.class.getName())) {
        RepositorioPersonaHumana instance = new RepositorioPersonaHumana();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioPersonaJuridica.class.getName())) {
        RepositorioPersonaJuridica instance = new RepositorioPersonaJuridica();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDonacionDinero.class.getName())) {
        RepositorioDonacionDinero instance = new RepositorioDonacionDinero();
        instances.put(componentName, instance);
      } else if (componentName.equals(RepositorioDonacionVianda.class.getName())) {
        RepositorioDonacionVianda instance = new RepositorioDonacionVianda();
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
      }
    }

    return (T) instances.get(componentName);
  }
}