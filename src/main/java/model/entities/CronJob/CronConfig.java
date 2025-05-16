package model.entities.CronJob;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CronConfig {

  private static final String CONFIG_FILE = "src/main/java/utils/cron.properties";
  private static final String PROPERTY_KEY = "cron.interval";
  private static final String DEFAULT_VALUE = "normal"; // Por defecto, será "normal"

  // Método que lee la configuración desde el archivo
  public static String getCronInterval() {
    Properties properties = new Properties();
    String cronInterval = DEFAULT_VALUE; // Valor por defecto

    try (InputStream input = new FileInputStream(CONFIG_FILE)) {
      properties.load(input);
      cronInterval = properties.getProperty(PROPERTY_KEY, DEFAULT_VALUE);
    } catch (IOException e) {
      System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
    }

    return cronInterval;
  }

  // Método que devuelve el intervalo en segundos
  public static int getIntervalInSeconds() {
    String cronInterval = getCronInterval();

    if (cronInterval.equals("fast")) {
      return 5;
    } else return 60 * 60 * 24 * 7; // 1 semana en segundos (7 días)
  }
}

