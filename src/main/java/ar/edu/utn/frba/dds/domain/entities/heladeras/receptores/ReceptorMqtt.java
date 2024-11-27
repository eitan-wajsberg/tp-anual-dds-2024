package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class ReceptorMqtt implements IMqttMessageListener, Runnable {
  private MqttClient client;
  private String brokerUrl;
  private String topic;
  private String username;
  private String password;

  public ReceptorMqtt(String brokerUrl, String topic, String username, String password) {
    this.brokerUrl = brokerUrl;
    this.topic = topic;
    this.username = username;
    this.password = password;
  }

  @Override
  public void run() {
    try {
      client = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());
      MqttConnectOptions connOpts = new MqttConnectOptions();

      connOpts.setCleanSession(true);
      connOpts.setUserName(this.username);
      connOpts.setPassword(this.password.toCharArray());


      client.connect(connOpts);
      System.out.println("Connected to broker: " + brokerUrl);

      client.subscribe(topic, this);
      System.out.println("Listening to topic: " + topic);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void messageArrived(String topic, MqttMessage mensaje) {
    String payload = new String(mensaje.getPayload());
    System.out.println("Message received on topic: " + topic);
    try {
      procesarMensaje(payload);
    } catch (Exception e) {
      System.err.println("Error processing message: " + e.getMessage());
    }
  }

  protected abstract void procesarMensaje(String payload);
}
