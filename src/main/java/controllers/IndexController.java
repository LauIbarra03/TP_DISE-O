package controllers;

import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioPersonaVulnerable;
import model.repositories.RepositorioUsuario;
import model.repositories.RepositorioViandas;

public class IndexController extends BaseController {

  private final RepositorioColaborador repositorioColaborador;
  private final RepositorioViandas repositorioVianda;
  private final RepositorioHeladera repositorioHeladera;
  private final RepositorioPersonaVulnerable repositorioPersonaVulnerable;

  public IndexController(RepositorioColaborador repositorioColaborador, RepositorioViandas repositorioVianda, RepositorioHeladera repositorioHeladera, RepositorioPersonaVulnerable repositorioPersonaVulnerable, RepositorioUsuario repositorioUsuario) {
    super(repositorioUsuario);
    this.repositorioColaborador = repositorioColaborador;
    this.repositorioVianda = repositorioVianda;
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
  }

  public void indexRender(Context context) {
    Map<String, Object> model = new HashMap<>();
    Long totalColaboradores = repositorioColaborador.contarColaboradores();
    Long viandasEntregadas = repositorioVianda.contarViandasEntregadas();
    Long heladerasActivas = repositorioHeladera.contarHeladerasActivas();
    Long personasAsistidas = repositorioPersonaVulnerable.contarPersonasAsistidas();

    model.put("totalColaboradores", totalColaboradores);
    model.put("viandasEntregadas", viandasEntregadas);
    model.put("heladerasActivas", heladerasActivas);
    model.put("personasAsistidas", personasAsistidas);


    renderWithUser(context, "panel.hbs", model);
  }
}
