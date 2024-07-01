package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Optional;

@Getter
public class ReceptorTemperatura implements MqttCallback {

    private MqttClient client;
    private RepositorioHeladera repositorioHeladeras;

    public ReceptorTemperatura(String brokerUrl, String clientId, RepositorioHeladera repositorioHeladeras) throws MqttException {
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
        // LOGICA DE FALLA DE CONEXION
    }

    // FORMATO DE MENSAJE: [IdHeladera,TipoDeMensaje:Valor]
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("Mensaje recibido: " + payload);

        try {
            String[] partes = dividirPayload(payload);
            if (partes != null) {
                Long idHeladera = Long.parseLong(partes[0]);
                String tipoMensaje = partes[1];
                int valor = Integer.parseInt(partes[2]);

                procesarMensaje(idHeladera, tipoMensaje, valor);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el valor a entero: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    private String[] dividirPayload(String payload) {
        String[] partes = payload.split(",");
        if (partes.length == 2) {
            String[] parteDivididas = partes[0].split(":");
            if (parteDivididas.length == 2) {
                return new String[]{parteDivididas[0], parteDivididas[1], partes[1]};
            } else {
                System.err.println("Formato de IdHeladera,TipoDeMensaje incorrecto");
            }
        } else {
            System.err.println("Formato de payload incorrecto");
        }
        return null;
    }

    private void procesarMensaje(Long idHeladera, String tipoMensaje, int valor) {
            Optional<Heladera> optionalHeladera = repositorioHeladeras.buscarPorId(idHeladera);
            if (optionalHeladera.isPresent()) {
                Heladera heladera = optionalHeladera.get();
                switch (tipoMensaje) {
                    case "Temperatura":
                        heladera.cambiarTemperatura(valor);
                        repositorioHeladeras.actualizar(heladera);
                        break;
                    default:
                        System.err.println("Tipo de mensaje no reconocido: " + tipoMensaje);
                        break;
                }
            } else {
                System.err.println("Heladera no encontrada para el ID: " + idHeladera);
            }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Lógica para manejar la confirmación de entrega de un mensaje publicado
    }

}
