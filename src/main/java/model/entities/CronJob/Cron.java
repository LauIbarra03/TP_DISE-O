package model.entities.CronJob;

import config.ServiceLocator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.GeneradorDeReportes;

public class Cron implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    ServiceLocator.instanceOf(GeneradorDeReportes.class).generarReportes();
  }
}
