package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;

import java.util.List;
import java.util.Optional;

@Getter
public class ReceptorMovimiento  implements MqttCallback {

    private MqttClient client;
    private RepositorioHeladera repositorioHeladeras;

    public ReceptorMovimiento(String brokerUrl, String clientId, RepositorioHeladera repositorioHeladeras) throws MqttException {
        this.repositorioHeladeras = repositorioHeladeras;
        this.client = new MqttClient(brokerUrl, clientId);
        this.client.setCallback(this);
        this.client.connect();
    }

    public void subscribe(String topic) throws MqttException {
        client.subscribe(topic);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // Lógica para manejar la pérdida de conexión
    }
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("Mensaje recibido: " + payload);

        try {
            // Asumiendo que el payload tiene el formato "heladeraId:temperatura"
            String[] parts = payload.split(":");
            if (parts.length == 2) {
                Long heladeraId = Long.parseLong(parts[0]);
                float temperatura = Float.parseFloat(parts[1]);
                Optional<Heladera> optionalHeladera = repositorioHeladeras.buscarPorId(heladeraId);
                if (optionalHeladera.isPresent()) {
                    Heladera heladera = optionalHeladera.get();
                    heladera.cambiarTemperatura(temperatura);
                } else {
                    System.err.println("Heladera no encontrada para el ID: " + heladeraId);
                }
            } else {
                System.err.println("Formato de payload incorrecto");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir la temperatura o el ID de la heladera: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Lógica para manejar la confirmación de entrega de un mensaje publicado
    }

}


