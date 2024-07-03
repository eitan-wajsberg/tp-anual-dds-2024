package ar.edu.utn.frba.dds.domain.adapters;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class AdaptadaWhatsAppTwillio implements AdapterWhatsApp {

  public static final String ACCOUNT_SID = "AC2f7dbacc4470d9c634115c9bad014"; //No debería estar acá
  public static final String AUTH_TOKEN = "3eb76ab54c958f19c11157908314d453";


  @Override
  public void enviar(String telefonoUsuario, String texto) {

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    String telefonoDeEnvio = "+17722962532"; // My twillio's phone

    Message message = Message.creator(
            new PhoneNumber(telefonoUsuario),
            new PhoneNumber(telefonoDeEnvio),
            texto)
        .create();
  }

}