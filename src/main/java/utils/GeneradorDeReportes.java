package utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Colaborador.Colaborador;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;
import model.entities.GeneradorInformes.GeneradorPDF;
import model.entities.GeneradorReportes.Reporte;
import model.entities.GeneradorReportes.ReporteFallasHeladera;
import model.entities.GeneradorReportes.ReporteViandasDonadas;
import model.entities.GeneradorReportes.ReporteViandasRetiradasOColocadasEnHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Heladera.RegistroViandasHeladera;
import model.entities.Incidente.Incidente;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioRegistroViandasHeladera;

public class GeneradorDeReportes {
  private final RepositorioContribucion repositorioContribucion;
  private final RepositorioRegistroViandasHeladera repositorioRegistroViandasHeladera;
  private final RepositorioDeIncidentes repositorioDeIncidentes;

  public GeneradorDeReportes(RepositorioContribucion repositorioContribucion, RepositorioRegistroViandasHeladera repositorioRegistroViandasHeladera, RepositorioDeIncidentes repositorioDeIncidentes) {
    this.repositorioContribucion = repositorioContribucion;
    this.repositorioRegistroViandasHeladera = repositorioRegistroViandasHeladera;
    this.repositorioDeIncidentes = repositorioDeIncidentes;
  }

  private static List<String> obtenerFallasHeladera() {
    RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    List<Incidente> incidentes = repositorioDeIncidentes.buscarFallasDesdeFecha(LocalDate.now().minusDays(7));
    List<Tuple<Heladera, Integer>> heladerasConFallas = new ArrayList<Tuple<Heladera, Integer>>();
    for (Incidente incidente : incidentes) {
      Heladera heladeraConFalla = incidente.getHeladera();
      if (heladerasConFallas.stream().map(h -> h.x).toList().contains(heladeraConFalla)) {
        int indiceHeladera = heladerasConFallas.stream().map(h -> h.x).toList().indexOf(heladeraConFalla);
        heladerasConFallas.set(indiceHeladera, new Tuple<Heladera, Integer>(
                heladerasConFallas.get(indiceHeladera).x,
                heladerasConFallas.get(indiceHeladera).y + 1
            )
        );
      } else {
        heladerasConFallas.add(new Tuple<Heladera, Integer>(
            heladeraConFalla,
            1));
      }
    }

    List<String> stringFallasPorHeladeras = new ArrayList<String>();
    for (Tuple<Heladera, Integer> heladeraConFallas : heladerasConFallas) {
      stringFallasPorHeladeras.add("Heladera " + heladeraConFallas.x.getId() + ": " + heladeraConFallas.y);
    }
    return stringFallasPorHeladeras;
  }

  private static List<String> obtenerViandasColocadasRetiradasPorHeladera() {
    RepositorioRegistroViandasHeladera repositorioRegistroViandasHeladera = new RepositorioRegistroViandasHeladera();
    List<RegistroViandasHeladera> registrosDeViandas = repositorioRegistroViandasHeladera.buscarPorFecha(LocalDate.now().minusDays(7));

    Map<Heladera, List<Integer>> heladerasModificadas = new HashMap<>();

    for (RegistroViandasHeladera registroDeViandas : registrosDeViandas) {
      Heladera heladeraModificada = registroDeViandas.getHeladera();
      int cantidad = registroDeViandas.getCantidad();

      // Si la heladera ya está en el map, actualizamos las estadísticas
      heladerasModificadas.putIfAbsent(heladeraModificada, new ArrayList<>(Arrays.asList(0, 0)));

      List<Integer> stats = heladerasModificadas.get(heladeraModificada);

      if (cantidad > 0) {
        stats.set(0, stats.get(0) + cantidad); // Se suman las viandas colocadas
      } else {
        stats.set(1, stats.get(1) + Math.abs(cantidad)); // Se suman las viandas retiradas
      }
    }

    List<String> stringHeladerasModificadas = new ArrayList<>();
    for (Map.Entry<Heladera, List<Integer>> entry : heladerasModificadas.entrySet()) {
      Heladera heladera = entry.getKey();
      List<Integer> stats = entry.getValue();
      stringHeladerasModificadas.add("---- " + "Heladera: " + heladera.getId() + " ----");
      stringHeladerasModificadas.add("Colocadas: " + stats.get(0));
      stringHeladerasModificadas.add("Retiradas: " + stats.get(1));
    }

    return stringHeladerasModificadas;
  }

  private static List<String> obtenerViandasDonadasPorColaborador() {
    RepositorioContribucion repositorioContribucion = new RepositorioContribucion();
    List<DonacionVianda> donaciones = repositorioContribucion.buscarDonacionPorFecha(LocalDate.now().minusDays(7));

    List<Tuple<Colaborador, Integer>> colaboradoresDonantes = new ArrayList<Tuple<Colaborador, Integer>>();
    for (DonacionVianda donacion : donaciones) {
      Colaborador colaboradorDonante = donacion.getColaborador();
      if (colaboradoresDonantes.stream().map(h -> h.x).toList().contains(colaboradorDonante)) {
        int indiceColaborador = colaboradoresDonantes.stream().map(h -> h.x).toList().indexOf(colaboradorDonante);
        colaboradoresDonantes.set(indiceColaborador, new Tuple<Colaborador, Integer>(
            colaboradoresDonantes.get(indiceColaborador).x,
            colaboradoresDonantes.get(indiceColaborador).y + donacion.getViandas().size()
        ));
      } else {
        colaboradoresDonantes.add(new Tuple<Colaborador, Integer>(
            donacion.getColaborador(),
            donacion.getViandas().size()
        ));
      }
    }

    List<String> stringColaboradoresDonantes = new ArrayList<String>();
    for (Tuple<Colaborador, Integer> colaboradorDonante : colaboradoresDonantes) {
      stringColaboradoresDonantes.add(
          colaboradorDonante.x.getApellido()
              + ", "
              + colaboradorDonante.x.getNombre()
              + ": "
              + colaboradorDonante.y);
    }
    return stringColaboradoresDonantes;
  }

  public void generarReportes() {
    // Datos para los reportes
    List<String> datosFallasHeladera = obtenerFallasHeladera();
    List<String> datosViandasHeladera = obtenerViandasColocadasRetiradasPorHeladera();
    List<String> datosViandasDonadas = obtenerViandasDonadasPorColaborador();

    // Instanciar reportes
    Reporte reporteFallasHeladera = new ReporteFallasHeladera(datosFallasHeladera);
    Reporte reporteViandasHeladera = new ReporteViandasRetiradasOColocadasEnHeladera(datosViandasHeladera);
    Reporte reporteViandasDonadas = new ReporteViandasDonadas(datosViandasDonadas);

    // Generar PDFs
    GeneradorPDF generadorPDF = new GeneradorPDF();
    try {
      generadorPDF.generarReportePDF(reporteFallasHeladera, "src/main/resources/public/reportes/reporteFallasHeladera.pdf");
      generadorPDF.generarReportePDF(reporteViandasHeladera, "src/main/resources/public/reportes/reporteViandasDonadas.pdf");
      generadorPDF.generarReportePDF(reporteViandasDonadas, "src/main/resources/public/reportes/reporteViandasHeladera.pdf");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
