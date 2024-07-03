package ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublicadorSolicitudApertura {
  private static PublicadorSolicitudApertura instancia;

  public static PublicadorSolicitudApertura getInstance() {
    if (instancia == null) {
      instancia = new PublicadorSolicitudApertura();
    }
    return instancia;
  }

  public void publicarSolicitudApertura(String codigoTarjeta, LocalDateTime fecha, Long idHeladera) {
    // FIXME: La ruta del broker debe compartirse con los receptores y utilizar distintos topicos?
    String broker = "tcp://your-broker-address:1883";
    String topic = "mqqt/heladeras/" + idHeladera;
    int cantidadHoras = HorasParaEjecutarAccion.getInstance().getHorasParaEjecutarAccion();
    String contenido = codigoTarjeta + " " + fecha.plusHours(cantidadHoras);
    int qos = 2;

    try {
      MqttClient client = new MqttClient(broker, "SMAACVS");
      client.connect();

      MqttMessage message = new MqttMessage(contenido.getBytes());
      message.setQos(qos);
      client.publish(topic, message);

      client.disconnect();
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }
}
