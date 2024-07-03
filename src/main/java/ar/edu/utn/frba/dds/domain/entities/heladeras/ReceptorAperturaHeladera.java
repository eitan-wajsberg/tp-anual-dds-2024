package ar.edu.utn.frba.dds.domain.entities.heladeras;

import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import java.util.Optional;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ReceptorAperturaHeladera implements IMqttMessageListener {
  IRepositorioHeladera repositorioHeladeras;

  public ReceptorAperturaHeladera(RepositorioHeladera repositorioHeladeras) {
    this.repositorioHeladeras = repositorioHeladeras;
  }

  public void messageArrived(String topic, MqttMessage mensaje) { // formato: idHeladera | codigoTarjeta
    try{
      String[] payload = dividirPayload(mensaje.toString());
      if(payload != null){
        Long idHeladera = Long.parseLong(payload[0]);
        String tipoMensaje = payload[1];
        String codigoTarjeta = payload[2];

        procesarMensaje(idHeladera, tipoMensaje, codigoTarjeta);
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

  private void procesarMensaje(Long idHeladera, String tipoMensaje, String codigoTarjeta) {
    Optional<Heladera> optionalHeladera = repositorioHeladeras.buscarPorId(idHeladera);
    if (optionalHeladera.isPresent()) {
      Heladera heladera = optionalHeladera.get();
      if (!tipoMensaje.equals("Apertura")) {
        System.err.println("Tipo de mensaje no reconocido: " + tipoMensaje);
      } else {
        heladera.validarApertura(codigoTarjeta);
      }
    } else {
      System.err.println("Heladera no encontrada para el ID: " + idHeladera);
    }
  }
}