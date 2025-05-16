package model.entities.GeneradorReportes;

import java.util.List;

public class ReporteViandasRetiradasOColocadasEnHeladera extends Reporte {

  public ReporteViandasRetiradasOColocadasEnHeladera(List<String> datos) {
    super(datos);
  }

  @Override
  public String obtenerTitulo() {
    return "Reporte de Viandas Retiradas/Colocadas por Heladera";
  }
}
