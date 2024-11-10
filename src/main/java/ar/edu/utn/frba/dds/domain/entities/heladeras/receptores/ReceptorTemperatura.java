package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorHeladera;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.*;

import java.util.Optional;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Getter
public class ReceptorTemperatura implements IMqttMessageListener, Runnable {
    private MqttClient client;
    private String brokerUrl;
    private String topic;
    private Timer timer;
    private final int TIMEOUT_MS = 1 * 60 * 1000;
    private Map<String, Long> ultimasRecibidas = new HashMap<>();
    private ControladorIncidenteHeladera controladorIncidenteHeladera = ServiceLocator.instanceOf(ControladorIncidenteHeladera.class);
    private ControladorHeladera controladorHeladera = ServiceLocator.instanceOf(ControladorHeladera.class);
    public ReceptorTemperatura(String brokerUrl, String topic) throws MqttException {
        this.brokerUrl = brokerUrl;
        this.topic = topic;
        this.timer = new Timer(true);
    }
    @Override
    public void run() {
        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            client.connect(connOpts);
            System.out.println("Connected to broker: " + brokerUrl);

            client.subscribe(topic, this);
            System.out.println("MQTT Receiver is running and listening to topic: " + topic);
            comenzarChequeoInactividad();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // FORMATO DE MENSAJE: [IdHeladera,TipoDeMensaje:Valor]
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        String payload = new String(mqttMessage.getPayload());
        System.out.println("Mensaje recibido: " + payload);
        try {
            String[] partes = dividirPayload(payload);
            if (partes != null) {
                String idHeladera = partes[0];
                String tipoMensaje = partes[1];
                String valor = partes[2];
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

    private void procesarMensaje(String idHeladera, String tipoMensaje, String valor) {
        ultimasRecibidas.put(idHeladera, System.currentTimeMillis());
        if (!tipoMensaje.equals("Temperatura")) {
                System.err.println("Tipo de mensaje no reconocido: " + tipoMensaje);
        }
        this.controladorHeladera.actualizarTemperatura(idHeladera, valor);
    }
    private void comenzarChequeoInactividad() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();

                for (Map.Entry<String, Long> entry : ultimasRecibidas.entrySet()) {
                    String fridgeId = entry.getKey();
                    long lastReceivedTime = entry.getValue();

                    if (currentTime - lastReceivedTime > TIMEOUT_MS) {
                        controladorIncidenteHeladera.procesarFallaConexion(fridgeId);
                    }
                }
            }
        }, 0, TIMEOUT_MS);
    }
}
