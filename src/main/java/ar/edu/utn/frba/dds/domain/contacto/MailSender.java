package ar.edu.utn.frba.dds.domain.contacto;

import ar.edu.utn.frba.dds.domain.adapters.AdapterMail;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender implements AdapterMail {

  private static final String username = System.getenv("USER_DDS_TPA");
  private static final String password = System.getenv("PASSWORD_DDS_TPA");


  @Override
  public void enviar(Mensaje mensaje, String correo) throws MessagingException {
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    System.out.println(username);
    System.out.println(password);

    Session session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(username));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
    message.setSubject(mensaje.getAsunto());
    message.setText(mensaje.getCuerpo());

    Transport.send(message);
  }
}
