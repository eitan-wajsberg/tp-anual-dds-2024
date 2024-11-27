package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorHeladera;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import lombok.Getter;
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
public class ReceptorTemperatura extends ReceptorMqtt {
    private Timer timer;
    private final int TIMEOUT_MS = 1 * 60 * 1000;
    private Map<String, Long> ultimasRecibidas = new HashMap<>();
    private Map<String, JobKey> heladerasJobs = new HashMap<>();
    private ControladorIncidenteHeladera controladorIncidenteHeladera = ServiceLocator.instanceOf(ControladorIncidenteHeladera.class);
    private ControladorHeladera controladorHeladera = ServiceLocator.instanceOf(ControladorHeladera.class);
    private RepositorioHeladera repositorioHeladera = ServiceLocator.instanceOf(RepositorioHeladera.class);
    private Scheduler scheduler;
    public ReceptorTemperatura(String brokerUrl, String topic, String username, String password) {
        super(brokerUrl,topic, username, password);
        //iniciarJobParaHeladeras();
    }
    @Override
    public void run() {
        super.run();
        SchedulerFactory sf = new StdSchedulerFactory();
        try {
            this.scheduler = sf.getScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        //iniciarJobParaHeladeras();
    }

    // FORMATO DE MENSAJE: [IdHeladera,ValorTemperatura]
    @Override
    protected void procesarMensaje(String payload) {
        String[] mensaje = payload.split(",");
        String idHeladera = mensaje[0];
        String temperatura = mensaje[1];
        procesarTemperatura(idHeladera, temperatura);
    }
    private void procesarTemperatura(String idHeladera, String valor) {
        ultimasRecibidas.put(idHeladera, System.currentTimeMillis());
        if (heladerasJobs.containsKey(idHeladera)) {
            actualizarTimestamp(idHeladera);
        }
        else{
            crearJobConexion(idHeladera);
        }
        this.controladorHeladera.actualizarTemperatura(idHeladera, valor, this);
    }

    private void crearJobConexion(String idHeladera) {
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

    public void eliminarJobDeHeladera(String idHeladera){
        JobKey jobKey = heladerasJobs.get(idHeladera);
        if (jobKey != null) {
            try {
                scheduler.deleteJob(jobKey);
                heladerasJobs.remove(idHeladera);
                System.out.println("Job eliminado para heladera: " + idHeladera);
            } catch (SchedulerException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al intentar eliminar el job de la heladera: " + idHeladera, e);
            }
        } else {
            System.err.println("No se encontró job asociado a la heladera: " + idHeladera);
        }
    }

    private void iniciarJobParaHeladeras() {
        try{
            List<Heladera> heladeras = repositorioHeladera.buscarTodos(Heladera.class);
            SchedulerFactory sf = new StdSchedulerFactory();
            this.scheduler = sf.getScheduler();
            heladeras.forEach(h -> iniciarJob(h));
        }catch(SchedulerException e){
        }
    }

    private void iniciarJob(Heladera heladera){
        String idHeladera = String.valueOf(heladera.getId());
        crearJobConexion(idHeladera);
    }

}
