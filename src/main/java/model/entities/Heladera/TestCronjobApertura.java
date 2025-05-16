package model.entities.Heladera;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.entities.Colaborador.Colaborador;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;
import model.entities.Heladera.SolicitudSobreHeladera.ReceptorDeSolicitudesDeApertura;
import model.entities.Heladera.SolicitudSobreHeladera.SolicitudDeAperturaPublisher;
import model.entities.Tarjeta.Tarjeta;
import model.entities.Vianda.EstadoVianda;
import model.entities.Vianda.Vianda;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioViandas;
import org.eclipse.paho.client.mqttv3.MqttException;

public class TestCronjobApertura {
  public static void main(String[] args) throws MqttException, IOException, InterruptedException {
    Long colaboradorId = Long.valueOf(args[0]);
    Long heladeraId = Long.valueOf(args[1]);
    Long viandaId = Long.valueOf(args[2]);

    RepositorioHeladera repositorioHeladera = new RepositorioHeladera();
    RepositorioColaborador repositorioColaborador = new RepositorioColaborador();

    Heladera heladera = repositorioHeladera.buscarPorID(heladeraId).get();
    Integer cantViandasAnterior = heladera.getCantViandas();
    Colaborador colaborador = repositorioColaborador.buscarPorID(colaboradorId).get();
    Tarjeta tarjetaColaborador = colaborador.getTarjeta();

    ReceptorDeSolicitudesDeApertura receptorDeSolicitudesDeApertura = new ReceptorDeSolicitudesDeApertura(heladera);
    SolicitudDeAperturaPublisher solicitudDeAperturaPublisher = new SolicitudDeAperturaPublisher(heladera, tarjetaColaborador);

    Thread.sleep(1000);

    if (cantViandasAnterior < heladera.getCantViandas()) {
      RepositorioViandas repositorioViandas = new RepositorioViandas();
      Vianda vianda = repositorioViandas.buscarPorId(viandaId).get();
      List<Vianda> viandas = new ArrayList<>();
      viandas.add(vianda);
      vianda.setColaborador(colaborador);
      vianda.setHeladera(heladera);
      vianda.setEstadoVianda(EstadoVianda.Entregada);
      DonacionVianda donacionVianda = new DonacionVianda(colaborador, LocalDateTime.now(), viandas);
      colaborador.agregarContribucion(donacionVianda);

      RepositorioContribucion repositorioContribucion = new RepositorioContribucion();
      repositorioContribucion.guardar(donacionVianda);
    }

    repositorioHeladera.modificar(heladera);
    repositorioColaborador.modificar(colaborador);
  }
}