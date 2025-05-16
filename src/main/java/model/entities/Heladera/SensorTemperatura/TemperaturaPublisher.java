package model.entities.Heladera.SensorTemperatura;

import java.util.logging.Logger;
import model.entities.Heladera.Heladera;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class TemperaturaPublisher {
  private static final Logger logger = Logger.getLogger(TemperaturaPublisher.class.getName());
  private final String broker = "ssl://11073dbbd92041b5b365399bce5974eb.s1.eu.hivemq.cloud:8883";
  private final String mensaje;
  private final Heladera heladera;
  MemoryPersistence persistence = new MemoryPersistence();
  private String clientId = "MovimientoPublisher_";
  private String topic = "dds2024/grupo3/heladeras/temperatura/";

  public TemperaturaPublisher(Heladera heladera, String mensaje) throws MqttException {
    this.heladera = heladera;
    this.mensaje = mensaje;
    clientId += heladera.getId().toString();
    this.topic += heladera.getTopic();

    try {
      MqttClient client = new MqttClient(broker, MqttClient.generateClientId());
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setUserName("dds_grupo12");
      connOpts.setPassword("Contraseña1234".toCharArray());
      connOpts.setCleanSession(true);
      client.connect(connOpts);

      MqttMessage message = new MqttMessage(mensaje.getBytes());
      message.setQos(2);
      client.publish(topic, message);
      client.disconnect();

      logger.info("Mensaje enviado al topic " + topic);
    } catch (MqttException e) {
      logger.severe("Error al enviar un mensaje al topic + " + topic + "\n" +
          "reason " + e.getReasonCode() + "\n" +
          "msg " + e.getMessage() + "\n");
    }
  }
}
