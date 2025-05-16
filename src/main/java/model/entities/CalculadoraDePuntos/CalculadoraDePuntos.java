package model.entities.CalculadoraDePuntos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import lombok.Getter;
import model.entities.Colaborador.Colaborador;
import model.entities.Contribucion.Contribucion;
import model.entities.Producto.Producto;

@Getter
public class CalculadoraDePuntos {
  private static final String COEFICIENTES_PATH = "src/main/java/utils/coeficientesPuntos.properties";
  private Properties coeficientes;

  public CalculadoraDePuntos() {
    try {
      cargarCoeficientes();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void cargarCoeficientes() throws IOException {
    coeficientes = new Properties();
    FileInputStream ip = new FileInputStream(COEFICIENTES_PATH);
    coeficientes.load(ip);
  }

  public double calcularPuntosContribuidor(Colaborador contribuidor) {
    double puntosTotales = 0;
    if (contribuidor.getContribucionesRealizadas() == null) {
      return 0;
    }
    for (Contribucion contribucion : contribuidor.getContribucionesRealizadas()) {
      // System.out.println("**************************** Procesando contribución: " + contribucion.getTipo() + " ****************************");
      puntosTotales += contribucion.calcularPuntos(coeficientes);
    }
    contribuidor.setPuntosTotales((int) puntosTotales);
    return puntosTotales;
  }

  public double calcularPuntosGastados(Colaborador contribuidor) {
    double puntosGastados = 0;
    if (contribuidor.getProductosComprados() == null) {
      return 0;
    }
    for (Producto producto : contribuidor.getProductosComprados()) {
      puntosGastados += producto.getCantidadDePuntos();
    }
    contribuidor.setPuntosGastados((int) puntosGastados);
    return puntosGastados;
  }


}