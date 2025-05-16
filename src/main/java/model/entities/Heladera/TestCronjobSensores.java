package model.entities.Heladera;

import model.entities.Heladera.SensorMovimiento.MovimientoPublisher;
import model.entities.Heladera.SensorMovimiento.SensorMovimiento;
import model.entities.Heladera.SensorTemperatura.SensorTemperatura;
import model.entities.Heladera.SensorTemperatura.TemperaturaPublisher;
import model.repositories.RepositorioHeladera;
import org.eclipse.paho.client.mqttv3.MqttException;

public class TestCronjobSensores {
  public static void main(String[] args) throws MqttException, InterruptedException {
    Long heladera1Id = Long.valueOf(args[0]);
    String mensajeMovimiento = args[1];
    Long heladera2Id = Long.valueOf(args[2]);
    String mensajeTemperatura = args[3];

    RepositorioHeladera repositorioHeladera = new RepositorioHeladera();

    Heladera heladera1 = repositorioHeladera.buscarPorID(heladera1Id).get();
    SensorMovimiento sensorMovimientoH1 = new SensorMovimiento(heladera1);

    Heladera heladera2 = repositorioHeladera.buscarPorID(heladera2Id).get();
    SensorTemperatura sensorTemperaturaH2 = new SensorTemperatura(heladera2);

    MovimientoPublisher mph1 = new MovimientoPublisher(heladera1, mensajeMovimiento);
    TemperaturaPublisher tph2 = new TemperaturaPublisher(heladera2, mensajeTemperatura);

    Thread.sleep(1000);

    repositorioHeladera.modificar(heladera1);
    repositorioHeladera.modificar(heladera2);
  }
}
