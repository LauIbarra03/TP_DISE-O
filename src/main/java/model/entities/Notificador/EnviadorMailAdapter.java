package model.entities.Notificador;

import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import lombok.Setter;

@Setter
public class EnviadorMailAdapter {
  private static final Logger logger = Logger.getLogger(EnviadorMailAdapter.class.getName());
  static EnviadorMailAdapter INSTANCE = null;
  private String asunto;

  public static EnviadorMailAdapter getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new EnviadorMailAdapter();
    }
    return INSTANCE;
  }

  public static EnviadorMailAdapter getINSTANCE() {
    return null;
  } // creo el metodo getINSTANCE porque sino el notificador test rompe

  public void enviar(String destino, String mensaje) {
    logger.info("Enviando mensaje a " + destino + "...");
    // Propiedades de la conexión
    Properties props = new Properties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.port", "587");
    props.setProperty("mail.smtp.user", "2024.tpa.grupo12@gmail.com");
    props.setProperty("mail.smtp.auth", "true");
    props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

    // Credenciales
    String mail = "2024.tpa.grupo12@gmail.com";
    String pass = "Tr4b4j0Pr4ct1c0.12";
    String appPass = "hkdttjyzssaezhkd";

    // Se prepara la sesion
    Session session = Session.getDefaultInstance(props);

    try {
      // Se construye el mensaje que se quiere enviar
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(mail));
      message.setRecipients(javax.mail.Message.RecipientType.TO, destino);
      message.setSubject(asunto);

      // Se crean las partes del mensaje
      MimeMultipart multipart = new MimeMultipart();
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText("<b>" + mensaje + "</b>", "ISO-8859-1", "html");
      multipart.addBodyPart(messageBodyPart);

      // Se establece el contenido del mensaje
      message.setContent(multipart);

      // Finalmente se envia el mensaje
      Transport t = session.getTransport("smtp");
      t.connect(mail, appPass);
      t.sendMessage(message, message.getAllRecipients());
      t.close();

      logger.info("Mensaje enviado a " + destino);
    } catch (Exception e) {
      logger.warning("No pudo enviarse el mensaje a" + destino + "\n" + e);
    }
  }
}


