package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorDonacionVianda;
import org.eclipse.paho.client.mqttv3.MqttClient;
public class ReceptorApertura extends ReceptorMqtt {
  private MqttClient client;
  private String brokerUrl;
  private String topic;
  public ReceptorApertura(String brokerUrl, String topic, String username, String password) {
    super(brokerUrl,topic, username, password);

  }
  @Override
  protected void procesarMensaje(String payload) {
    String [] partes = payload.split(",");
    String idHeladera = partes[0];
    String idColaborador = partes[1];
    String idVianda = partes[2];
    procesarApertura(Long.parseLong(idHeladera), Long.parseLong(idColaborador), Long.parseLong(idVianda));
  }
  private void procesarApertura(Long idHeladera, Long idColaborador, Long idVianda) {
    ControladorDonacionVianda controlador = ServiceLocator.instanceOf(ControladorDonacionVianda.class);
    controlador.procesarIngresoDeViandaEnHeladera(idHeladera, idColaborador, idVianda);
  }

}