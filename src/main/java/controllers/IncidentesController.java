package controllers;

import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Direccion.Localidad;
import model.entities.Incidente.Incidente;
import model.entities.Incidente.TipoIncidente;
import model.entities.Tecnico.Tecnico;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioUsuario;

public class IncidentesController extends BaseController {
  private final RepositorioLocalidad repositorioLocalidad;
  private final RepositorioDeIncidentes repositorioDeIncidentes;

  public IncidentesController(RepositorioUsuario repositorioUsuario, RepositorioLocalidad repositorioLocalidad, RepositorioDeIncidentes repositorioDeIncidentes) {
    super(repositorioUsuario);
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioDeIncidentes = repositorioDeIncidentes;
  }

  @Override
  public void index(Context context) {
    List<Localidad> localidadesTecnico = obtenerLocalidadesDelTecnico(context);
    List<Incidente> incidentesTotales = this.repositorioDeIncidentes.buscarTodosLosNoResueltos();

    List<Incidente> incidentes = new ArrayList<>();
    for (Localidad localidad : localidadesTecnico) {
      List<Incidente> incidentesPorLocalidad = incidentesTotales
          .stream()
          .filter(i -> i.getHeladera().getPuntoGeografico().getDireccion().getLocalidad().equals(localidad))
          .toList();
      incidentes.addAll(incidentesPorLocalidad);
    }

    Map<String, Object> model = new HashMap<>();
    model.put("incidentes", incidentes);
    model.put("titulo", "Listado de Incidentes");

    renderWithUser(context, "Incidentes/incidentes.hbs", model);
  }

  @Override
  public void show(Context context) {
    Incidente incidente = this.repositorioDeIncidentes.buscarPorId(Long.valueOf(context.pathParam("id")))
        .orElseThrow(() -> new IllegalArgumentException("Incidente no encontrado"));
    String tipoAlerta = null;
    if (incidente.getTipoIncidente() == TipoIncidente.Alerta) {
      tipoAlerta = switch (incidente.getTipoAlerta()) {
        case Fraude -> "Fraude";
        case FallaConexion -> "Falla en la conexión";
        case Temperatura -> "Temperatura fuera del rango permitido";
      };
    }

    Map<String, Object> model = new HashMap<>();
    model.put("incidente", incidente);
    model.put("tipoAlerta", tipoAlerta);

    renderWithUser(context, "Incidentes/detalle_incidente.hbs", model);
  }

  private List<Localidad> obtenerLocalidadesDelTecnico(Context context) {
    Usuario usuario = getSessionUser(context);
    Tecnico tecnico = usuario.getTecnico();
    List<Localidad> localidades = this.repositorioLocalidad.buscarTodos();
    List<Localidad> localidadesFiltradas = localidades.stream().filter(l -> l.getTecnicos().contains(tecnico)).toList();
    System.out.println(localidadesFiltradas);
    return localidadesFiltradas;
  }
}
