package model.entities.GeneradorReportes;

import java.util.List;

public class ReporteViandasDonadas extends Reporte {

  public ReporteViandasDonadas(List<String> datos) {
    super(datos);
  }

  @Override
  public String obtenerTitulo() {
    return "Reporte de Viandas Donadas por Colaborador";
  }
}
