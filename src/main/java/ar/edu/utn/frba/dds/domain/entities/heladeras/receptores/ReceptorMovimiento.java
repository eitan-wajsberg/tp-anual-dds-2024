package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.ControladorIncidenteHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import java.util.Optional;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ReceptorMovimiento implements IMqttMessageListener {
  @Override
  public void messageArrived(String topic, MqttMessage mensaje) throws Exception{ // formato: idHeladera | "fraude"
        try{
          String[] payload = dividirPayload(mensaje.toString());
          if(payload != null){
            Long idHeladera = Long.parseLong(payload[0]);
            String tipoMensaje = payload[1];
            System.out.println("se recibió el mensaje correctamente");
            procesarMensaje(idHeladera, tipoMensaje);
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

  private void procesarMensaje(Long idHeladera, String tipoMensaje) {

      if (!tipoMensaje.equals("Fraude")) {
        System.err.println("Tipo de mensaje no reconocido: " + tipoMensaje);
    } else {
        ControladorIncidenteHeladera controlador = ServiceLocator.instanceOf(ControladorIncidenteHeladera.class);
        controlador.procesarFraude(idHeladera);
    }
  }


}
