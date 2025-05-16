package model.entities.Heladera.SolicitudSobreHeladera;

import java.util.logging.Logger;
import model.entities.Heladera.Heladera;
import model.entities.Tarjeta.Tarjeta;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SolicitudDeAperturaPublisher {
  private static final Logger logger = Logger.getLogger(SolicitudDeAperturaPublisher.class.getName());
  private final String broker = "ssl://11073dbbd92041b5b365399bce5974eb.s1.eu.hivemq.cloud:8883";
  private final String clientId = MqttClient.generateClientId();
  private final String mensaje;
  private final Heladera heladera;
  private final Tarjeta tarjetaColaborador;
  private String topic = "dds2024/grupo3/tarjetasColaborador/";

  public SolicitudDeAperturaPublisher(Heladera heladera, Tarjeta tarjetaColaborador) throws MqttException {
    this.tarjetaColaborador = tarjetaColaborador;
    this.heladera = heladera;
    this.topic += heladera.getTopic();
    this.mensaje = tarjetaColaborador.getCodigo();

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
