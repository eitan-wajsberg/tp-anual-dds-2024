package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladera;

public class ReceptorMovimiento extends ReceptorMqtt {

  public ReceptorMovimiento(String brokerUrl, String topic, String username, String password) {
    super(brokerUrl,topic, username, password);

  }
  @Override
  protected void procesarMensaje(String payload) {
    try {
      Long idHeladera = Long.parseLong(payload);
      System.out.println("Procesando movimiento sospechoso en heladera: " + idHeladera);
      ControladorIncidenteHeladera controlador = ServiceLocator.instanceOf(ControladorIncidenteHeladera.class);
      controlador.procesarFraude(idHeladera);
    } catch (NumberFormatException e) {
      System.err.println("Invalid payload: " + payload);
    }
  }
}
