package model.entities.CronJob;

import config.ServiceLocator;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import utils.GeneradorDeReportes;

public class SchedulerConfig {

  public static void iniciarScheduler() throws SchedulerException {
    // Obtener el intervalo en segundos desde la configuración
    int intervalInSeconds = CronConfig.getIntervalInSeconds();

    // Crear un JobDetail para el Job
    JobDetail job = JobBuilder.newJob(Cron.class)
        .withIdentity("reporte", "reportes")
        .build();

    Trigger trigger;

    // Configurar el Trigger basado en el intervalo
    if (intervalInSeconds == 5) {
      // Si el intervalo es 5 segundos, configurar un cronjob que se repita cada 5 segundos
      trigger = TriggerBuilder.newTrigger()
          .withIdentity("triggerCada5Segundos", "reportes")
          .withSchedule(SimpleScheduleBuilder.simpleSchedule()
              .withIntervalInSeconds(intervalInSeconds) // Usamos el valor leído
              .repeatForever()) // Repite infinitamente
          .startNow() // Iniciar inmediatamente
          .build();
    } else {
      // Si el intervalo es 1 semana, configurar un cronjob semanal
      trigger = TriggerBuilder.newTrigger()
          .withIdentity("triggerSemanal", "reportes")
          .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.MONDAY, 0, 0)) // Lunes a las 00:00
          .startNow() // Iniciar inmediatamente
          .build();
    }

    // Crear y configurar el Scheduler
    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
    scheduler.start();
    scheduler.scheduleJob(job, trigger);

    //Primera ejecucion del generador de reportes
    ServiceLocator.instanceOf(GeneradorDeReportes.class).generarReportes();
  }
}
