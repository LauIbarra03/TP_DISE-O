package model.entities.Notificador;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EnviadorTelegramAdapter extends TelegramLongPollingBot {
  private final static String TELEGRAM_BOT_PATH = "src/main/java/ar/edu/utn/frba/dds/Utils/telegram.properties";
  private static final Logger logger = Logger.getLogger(EnviadorTelegramAdapter.class.getName());
  private static EnviadorTelegramAdapter instancia = null;
  private Properties properties;

  public static EnviadorTelegramAdapter getInstance() {
    if (instancia == null) {
      instancia = new EnviadorTelegramAdapter();
    }
    return instancia;
  }

  public void cargarProperties() throws IOException {
    properties = new Properties();
    FileInputStream ip = new FileInputStream(TELEGRAM_BOT_PATH);
    properties.load(ip);
  }

  @Override
  public String getBotUsername() {
    try {
      cargarProperties();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return properties.getProperty("BOT_USERNAME");
  }

  @Override
  public String getBotToken() {
    try {
      cargarProperties();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return properties.getProperty("BOT_TOKEN");
  }

  @Override
  public void onUpdateReceived(Update update) {
    String message = update.getMessage().getText(); // mensaje recibido
    Long chatId = update.getMessage().getChatId(); // identificacion del chat

    if (Objects.equals(message, "/start")) {
      sendMessage(generateSendMessage(chatId,
          "Bienvenidos al bot: " + getBotUsername() + "\n" +
              "Este es su chatID: " + chatId + "\n" +
              "Puede volver a visualizarlo con el comando /chatID\n" +
              "Puede ver el resto del comandos con /help"));
    } else if (Objects.equals(message, "/help")) {
      sendMessage(generateSendMessage(chatId,
          "Este es la lista de comandos que puede utilizar: \n" +
              "1) /suscripcion\n" +
              "2) /tecnico\n" +
              "3) /chatIP"));
    } else if (Objects.equals(message, "/suscripcion")) {
      sendMessage(generateSendMessage(chatId, "Se enviaron los datos al contribuidor" +
          "ACA SE ENVIARIAN LA NOTIFICACION AL CONTRIBUIDOR"));
    } else if (Objects.equals(message, "/tecnico")) {
      sendMessage(generateSendMessage(chatId, "Seleccione el tecnico a llamar: \n" +
          "ACA TENDRIA Q SALIR LA LISTA DE TECNICOS\n" +
          "/tecnico_1\n" +
          "/tecnico_2\n" +
          "/tecnico_3\n"));
      SendMessage sendMessage = new SendMessage();
      sendMessage.setReplyToMessageId(1);
    } else if ((Objects.equals(message, "/chatID"))) {
      sendMessage(generateSendMessage(chatId, "Su chatID es: " + chatId));

    } else {
      sendMessage(generateSendMessage(chatId, "Por favor ingrese un comando válido, con /help puede ver los disponibles"));
    }
  }

  public void sendMessage(SendMessage sendMessage) {
    try {
      logger.info("Enviando mensaje por telegram");
      execute(sendMessage);
      logger.info("Mensaje por telegram enviado");
    } catch (TelegramApiException e) {
      logger.warning("No pudo enviarse el mensaje por telegram");
    }
  }

  public void enviar(String destino, String mensaje) {
    SendMessage sendMessage = new SendMessage(destino, mensaje);
    sendMessage(sendMessage);
  }

  private SendMessage generateSendMessage(Long chatId, String message) {
    return new SendMessage(chatId.toString(), message);
  }

  @Override
  public void onRegister() {
    super.onRegister();
  }
}
