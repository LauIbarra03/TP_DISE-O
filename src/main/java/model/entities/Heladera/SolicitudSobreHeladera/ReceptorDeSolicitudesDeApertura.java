package model.entities.Heladera.SolicitudSobreHeladera;

import java.util.logging.Logger;
import model.entities.Heladera.Heladera;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ReceptorDeSolicitudesDeApertura implements IMqttMessageListener {
  private static final Logger logger = Logger.getLogger(ReceptorDeSolicitudesDeApertura.class.getName());
  private final String broker = "ssl://11073dbbd92041b5b365399bce5974eb.s1.eu.hivemq.cloud:8883";
  private final Heladera heladera;
  MemoryPersistence persistence = new MemoryPersistence();
  private String topic = "dds2024/grupo3/tarjetasColaborador/";

  public ReceptorDeSolicitudesDeApertura(Heladera heladera) {
    this.heladera = heladera;
    topic += heladera.getTopic();
    suscribirseAlBroker();
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    String idTarjeta = new String(message.getPayload());
    logger.info("Se recibio el id: " + idTarjeta);

    if (heladera.tienePermisos(idTarjeta)) {
      logger.info("Tiene permisos");
    } else logger.info("No tiene permisos");

  }

  private void suscribirseAlBroker() {
    try {
      MqttClient client = new MqttClient(broker, MqttClient.generateClientId(), persistence);
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setUserName("dds_grupo12");
      connOpts.setPassword("Contraseña1234".toCharArray());
      connOpts.setCleanSession(true);
      client.connect(connOpts);
      client.subscribe(topic, this);
      logger.info("Conexion y suscripcion exitosa al topic " + topic);
    } catch (MqttException e) {
      logger.severe("Error en la suscripcion al topic + " + topic + "\n" +
          "reason " + e.getReasonCode() + "\n" +
          "msg " + e.getMessage() + "\n");
    }
  }
}
