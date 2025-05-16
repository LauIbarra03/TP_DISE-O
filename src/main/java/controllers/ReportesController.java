package controllers;

import io.javalin.http.Context;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioRegistroViandasHeladera;
import model.repositories.RepositorioUsuario;
import utils.GeneradorDeReportes;

public class ReportesController extends BaseController {
  private final RepositorioContribucion repositorioContribucion;
  private final RepositorioRegistroViandasHeladera repositorioRegistroViandasHeladera;
  private final RepositorioDeIncidentes repositorioDeIncidentes;

  public ReportesController(RepositorioUsuario repositorioUsuario, RepositorioContribucion repositorioContribucion, RepositorioRegistroViandasHeladera repositorioRegistroViandasHeladera, RepositorioDeIncidentes repositorioDeIncidentes) {
    super(repositorioUsuario);
    this.repositorioContribucion = repositorioContribucion;
    this.repositorioRegistroViandasHeladera = repositorioRegistroViandasHeladera;
    this.repositorioDeIncidentes = repositorioDeIncidentes;
  }

  @Override
  public void index(Context context) {

    Map<String, Object> model = new HashMap<>();

    model.put("titulo", "Reportes");

    renderWithUser(context, "Reportes/reportes.hbs", model);
  }

  public void generarReportes(Context context) {
    GeneradorDeReportes generadorDeReportes = new GeneradorDeReportes(repositorioContribucion, repositorioRegistroViandasHeladera, repositorioDeIncidentes);
    generadorDeReportes.generarReportes();

    Map<String, Object> model = new HashMap<>();
    model.put("success", true);

    renderWithUser(context, "Reportes/reportes.hbs", model);
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) throws IOException {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) throws IOException {

  }

  @Override
  public void delete(Context context) {

  }
}
