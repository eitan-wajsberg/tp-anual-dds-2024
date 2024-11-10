package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorHeladera;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.util.Date;
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
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@Getter
public class ReceptorTemperatura implements IMqttMessageListener, Runnable {
    private MqttClient client;
    private String brokerUrl;
    private String topic;
    private Timer timer;
    private final int TIMEOUT_MS = 1 * 60 * 1000;
    private Map<String, Long> ultimasRecibidas = new HashMap<>();
    private Map<String, JobKey> heladerasJobs = new HashMap<>();
    private ControladorIncidenteHeladera controladorIncidenteHeladera = ServiceLocator.instanceOf(ControladorIncidenteHeladera.class);
    private ControladorHeladera controladorHeladera = ServiceLocator.instanceOf(ControladorHeladera.class);
    private Scheduler scheduler;
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

            SchedulerFactory sf = new StdSchedulerFactory();
            this.scheduler = sf.getScheduler();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
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
        if (heladerasJobs.containsKey(idHeladera)) {
            actualizarTimestamp(idHeladera);
        }
        else{
            try {
                JobDetail job = JobBuilder.newJob(FallaConexionJob.class)
                    .withIdentity(idHeladera)
                    .usingJobData("idHeladera", idHeladera) // Pasar el idHeladera
                    .usingJobData("timestamp", System.currentTimeMillis()) // Pasar el timestamp
                    .build();
                Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(idHeladera + "_trigger")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(90)
                        .repeatForever())
                    .build();

                scheduler.scheduleJob(job, trigger);
                scheduler.start();
                this.heladerasJobs.put(idHeladera, job.getKey());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        this.controladorHeladera.actualizarTemperatura(idHeladera, valor);
    }
    public void actualizarTimestamp(String idHeladera) {
        JobKey jobKey = heladerasJobs.get(idHeladera);
        if (jobKey != null) {
            try {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey); // Obtiene el JobDetail por su JobKey
                if (jobDetail != null) {
                    JobDataMap dataMap = jobDetail.getJobDataMap(); // Accede al JobDataMap
                    dataMap.put("timestamp", System.currentTimeMillis()); // Actualiza el timestamp
                    System.out.println("Timestamp actualizado para heladera: " + idHeladera);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

}
