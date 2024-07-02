package ar.edu.utn.frba.dds.domain.entities.heladeras.receptores;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import java.util.Optional;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ReceptorMovimiento implements IMqttMessageListener {
  RepositorioHeladera repositorioHeladeras;
  public void messageArrived(String topic, MqttMessage mensaje){ // formato: idHeladera | fraude
        try{
          String[] payload = dividirPayload(mensaje.toString());
          if(payload != null){
            Long idHeladera = Long.parseLong(payload[0]);
            String tipoMensaje = payload[1];
            Boolean valor = Boolean.parseBoolean(payload[2]);

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
  private void procesarMensaje(Long idHeladera, String tipoMensaje, Boolean valor) {
    Optional<Heladera> optionalHeladera = repositorioHeladeras.buscarPorId(idHeladera);
    if (optionalHeladera.isPresent()) {
      Heladera heladera = optionalHeladera.get();
      if(tipoMensaje != "Fraude") {
        System.err.println("Tipo de mensaje no reconocido: " + tipoMensaje);
      }
      else if (valor){
        heladera.recibirAlertaFraude();
      }
    } else {
      System.err.println("Heladera no encontrada para el ID: " + idHeladera);
    }
  }

}
