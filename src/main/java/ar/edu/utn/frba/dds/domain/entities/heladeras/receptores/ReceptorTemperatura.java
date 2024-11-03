package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Optional;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Getter
public class ReceptorTemperatura implements IMqttMessageListener, Runnable {

    private MqttClient client;
    private String brokerUrl;
    private String topic;
    private Timer timer;
    private final int TIMEOUT_MS = 5 * 60 * 1000;

    public ReceptorTemperatura(String brokerUrl, String clientId) throws MqttException {
        this.client = new MqttClient(brokerUrl, clientId);
        this.client.connect();
    }
    @Override
    public void run() {
        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            client.connect(connOpts);
            System.out.println("Connected to broker: " + brokerUrl);

            // Suscribirse al topic usando esta clase como listener
            client.subscribe(topic, this);
            System.out.println("MQTT Receiver is running and listening to topic: " + topic);
            resetTimer();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void resetTimer() {
        // Cancela cualquier tarea de alerta previa y programa una nueva
        timer.cancel(); // Cancela el temporizador anterior
        timer = new Timer(true); // Crear un nuevo temporizador en modo daemon
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

               //alertar a controlador
            }
        }, TIMEOUT_MS);
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
                System.out.println("Se recibió exitosamente el mensaje");
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
            String[] parteDivididas = partes[1].split(":");
            if (parteDivididas.length == 2) {
                return new String[]{partes[0], parteDivididas[0], parteDivididas[1]};
            } else {
                System.err.println("Formato de TipoDeMensaje:Valor incorrecto");
            }
        } else {
            System.err.println("Formato de payload incorrecto");
        }
        return null;
    }

    private void procesarMensaje(Long idHeladera, String tipoMensaje, int valor) {
        if (!tipoMensaje.equals("Temperatura")) {
                System.err.println("Tipo de mensaje no reconocido: " + tipoMensaje);
        }
        // controlador
    }

}
