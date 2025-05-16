package model.entities.GeneradorReportes;

import java.util.List;

public class ReporteFallasHeladera extends Reporte {
  public ReporteFallasHeladera(List<String> datos) {
    super(datos);
  }

  @Override
  public String obtenerTitulo() {
    return "Reporte de Fallas por Heladera";
  }
}
