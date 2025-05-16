package model.entities.GeneradorReportes;

import java.util.List;

public abstract class Reporte {
  protected List<String> datos; // Datos específicos para el reporte

  public Reporte(List<String> datos) {
    this.datos = datos;
  }

  // Método abstracto para obtener el título del reporte
  public abstract String obtenerTitulo();

  // Método común para generar el contenido del reporte
  public String generarContenido() {
    StringBuilder contenido = new StringBuilder();
    contenido.append(obtenerTitulo()).append("\n");
    // Iterar sobre los datos y agregarlos al contenido
    for (String dato : datos) {
      contenido.append(dato).append("\n");
    }
    return contenido.toString();
  }
}