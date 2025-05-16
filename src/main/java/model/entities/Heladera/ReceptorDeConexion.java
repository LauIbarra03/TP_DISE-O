package model.entities.Heladera;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import model.entities.Heladera.SensorTemperatura.SensorTemperatura;
import model.entities.Incidente.ProcesadorDeIncidentes;
import model.entities.Incidente.TipoAlerta;

public class ReceptorDeConexion {
  private final SensorTemperatura sensorTemperatura;
  private final ScheduledExecutorService scheduler;

  public ReceptorDeConexion(SensorTemperatura sensorTemperatura, ProcesadorDeIncidentes procesadorDeIncidentes) {
    this.sensorTemperatura = sensorTemperatura;
    this.scheduler = Executors.newScheduledThreadPool(1);
    verificarConexion();
  }

  private void verificarConexion() {
    scheduler.scheduleAtFixedRate(() -> {
      LocalDateTime fechaYHoraDeUltimaTemperatura = sensorTemperatura.getFechaYHoraDeUltimaTemperatura();
      if (fechaYHoraDeUltimaTemperatura != null) {
        Duration duration = Duration.between(fechaYHoraDeUltimaTemperatura, LocalDateTime.now());
        if (duration.toMinutes() > 5) {
          try {
            dispararAlerta();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }, 0, 5, TimeUnit.MINUTES);
  }

  private void dispararAlerta() throws IOException {
    sensorTemperatura.getProcesadorDeIncidentes().sucedeIncidente(TipoAlerta.FallaConexion, sensorTemperatura.getHeladera());
  }
}

