package model.entities.Heladera.SensorMovimiento;

import java.io.IOException;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import model.entities.Heladera.Heladera;
import model.entities.Incidente.ProcesadorDeIncidentes;
import model.entities.Incidente.TipoAlerta;
import model.entities.Incidente.TipoIncidente;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Getter
@Setter
public class SensorMovimiento implements IMqttMessageListener {
  private static final Logger logger = Logger.getLogger(SensorMovimiento.class.getName());
  MemoryPersistence persistence = new MemoryPersistence();
  private String topic = "dds2024/grupo3/heladeras/movimiento/";
  private String broker = "ssl://11073dbbd92041b5b365399bce5974eb.s1.eu.hivemq.cloud:8883";
  private String clientId = "SensorMovimiento_";
  private Heladera heladera;

  public SensorMovimiento(Heladera heladera) {
    this.heladera = heladera;
    clientId += heladera.getId().toString();
    topic += heladera.getTopic();
    suscribirseAlBroker();

    System.out.println("SensorMovimiento escuchando en topic: " + topic);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    logger.info("Se ha detectado movimiento!");
    if (heladera.getIncidentes().stream().anyMatch(i ->
        !i.getEstaResuelto()
            && i.getTipoIncidente() == TipoIncidente.Alerta
            && i.getTipoAlerta() == TipoAlerta.Fraude)) {
      return;
    }
    dispararAlerta();
  }

  private void dispararAlerta() throws IOException {
    ProcesadorDeIncidentes procesadorDeIncidentes = new ProcesadorDeIncidentes();
    procesadorDeIncidentes.sucedeIncidente(TipoAlerta.Fraude, heladera);
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
