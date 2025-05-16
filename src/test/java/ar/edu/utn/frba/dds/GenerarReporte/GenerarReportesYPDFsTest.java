package ar.edu.utn.frba.dds.GenerarReporte;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.junit.Test;

public class GenerarReportesYPDFsTest {
  @Test
  public void generarReportesYPDFs() {
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
      generadorPDF.generarReportePDF(reporteFallasHeladera, "src/test/resources/Reportes/reporteFallasHeladera.pdf");
      generadorPDF.generarReportePDF(reporteViandasHeladera, "src/test/resources/Reportes/reporteViandasHeladera.pdf");
      generadorPDF.generarReportePDF(reporteViandasDonadas, "src/test/resources/Reportes/reporteViandasDonadas.pdf");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<String> obtenerFallasHeladera() {
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

  private List<String> obtenerViandasColocadasRetiradasPorHeladera() {
    RepositorioRegistroViandasHeladera repositorioRegistroViandasHeladera = new RepositorioRegistroViandasHeladera();
    List<RegistroViandasHeladera> registrosDeViandas = repositorioRegistroViandasHeladera.buscarPorFecha(LocalDate.now().minusDays(7));

    List<Tuple<Heladera, List<Integer>>> heladerasModificadas = new ArrayList<Tuple<Heladera, List<Integer>>>();
    for (RegistroViandasHeladera registroDeViandas : registrosDeViandas) {
      Heladera heladeraModificada = registroDeViandas.getHeladera();
      if (heladerasModificadas.stream().map(h -> h.x).toList().contains(heladeraModificada)) {
        int indiceHeladera = heladerasModificadas.stream().map(h -> h.x).toList().indexOf(heladeraModificada);
        List<Integer> stats = new ArrayList<>();
        List<Integer> statsActuales = heladerasModificadas.get(indiceHeladera).y;
        if (registroDeViandas.getCantidad() > 0) {
          stats.add(statsActuales.get(0) + registroDeViandas.getCantidad());
          stats.add(statsActuales.get(1));
        } else {
          stats.add(statsActuales.get(0));
          stats.add(statsActuales.get(1 + registroDeViandas.getCantidad()));
        }

        heladerasModificadas.set(indiceHeladera, new Tuple<Heladera, List<Integer>>(
            heladerasModificadas.get(indiceHeladera).x,
            stats
        ));
      } else {
        List<Integer> stats = new ArrayList<>();
        if (registroDeViandas.getCantidad() > 0) {
          stats.add(registroDeViandas.getCantidad());
          stats.add(0);
        } else {
          stats.add(0);
          stats.add(Math.abs(registroDeViandas.getCantidad()));
        }

        heladerasModificadas.add(new Tuple<Heladera, List<Integer>>(
            heladeraModificada,
            stats
        ));
      }
    }

    List<String> stringHeladerasModificadas = new ArrayList<String>();
    for (Tuple<Heladera, List<Integer>> heladeraModificada : heladerasModificadas) {
      stringHeladerasModificadas.add("---- " + "Heladera: " + heladeraModificada.x.getId());
      stringHeladerasModificadas.add("Colocadas: " + heladeraModificada.y.get(0));
      stringHeladerasModificadas.add("Retiradas: " + heladeraModificada.y.get(1));
    }
    return stringHeladerasModificadas;
  }

  private List<String> obtenerViandasDonadasPorColaborador() {
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
}
