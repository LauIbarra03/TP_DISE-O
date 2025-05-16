package model.entities.Notificador;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class EnviadorWPPAdapter {
  private final static String WPP_PATH = "src/main/java/ar/edu/utn/frba/dds/Utils/wpp.properties";
  private static final Logger logger = Logger.getLogger(EnviadorWPPAdapter.class.getName());
  private static EnviadorWPPAdapter instancia = null;
  public Properties properties;

  public static EnviadorWPPAdapter getInstance() {
    if (instancia == null) {
      instancia = new EnviadorWPPAdapter();
    }
    return instancia;
  }

  public void cargarProperties() throws IOException {
    properties = new Properties();
    FileInputStream ip = new FileInputStream(WPP_PATH);
    properties.load(ip);
  }

  public void enviar(String nroDestino, String mensaje) throws IOException {
    try {
      cargarProperties();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String ACCOUNT_SID = properties.getProperty("ACCOUNT_SID");
    String AUTH_TOKEN = properties.getProperty("AUTH_TOKEN");
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
            new PhoneNumber("whatsapp:" + nroDestino),
            new PhoneNumber("whatsapp:+14155238886"),
            mensaje)
        .create();

    logger.info("Mensaje por whatsapp enviado con exito, SID:" + message.getSid());
  }
}

