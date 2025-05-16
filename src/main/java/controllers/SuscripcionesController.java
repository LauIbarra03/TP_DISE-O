package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.entities.Heladera.Heladera;
import model.entities.Suscripcion.DesperfectoHeladera.DesperfectoHeladera;
import model.entities.Suscripcion.MuchasViandas.MuchasViandas;
import model.entities.Suscripcion.PocasViandas.PocasViandas;
import model.entities.Suscripcion.Suscripcion;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioSuscripcion;
import model.repositories.RepositorioTipoSuscripcion;
import model.repositories.RepositorioUsuario;

public class SuscripcionesController extends BaseController {
  private final RepositorioSuscripcion repositorioSuscripcion;
  private final RepositorioHeladera repositorioHeladera;
  private final RepositorioTipoSuscripcion repositorioTipoSuscripcion;

  public SuscripcionesController(RepositorioSuscripcion repositorioSuscripcion, RepositorioHeladera repositorioHeladera, RepositorioUsuario repositorioUsuario,
                                 RepositorioTipoSuscripcion repositorioTipoSuscripcion) {
    super(repositorioUsuario);
    this.repositorioSuscripcion = repositorioSuscripcion;
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioTipoSuscripcion = repositorioTipoSuscripcion;
  }

  @Override
  public void index(Context context) {
    Usuario user = getSessionUser(context);

    List<Suscripcion> suscripciones = user.getTipoRol() == TipoRol.ADMIN ?
        this.repositorioSuscripcion.buscarTodos() :
        this.repositorioSuscripcion.buscarTodos(user.getColaborador().getId());

    Map<String, Object> model = new HashMap<>();
    model.put("suscripciones", suscripciones);
    model.put("titulo", "Suscripciones");

    renderWithUser(context, "suscripcion/suscripcion.hbs", model);
  }

  @Override
  public void show(Context context) {
    Map<String, Object> model = new HashMap<>();
    Long suscripcion_id = Long.parseLong(context.pathParam("id"));
    Suscripcion suscripcion = repositorioSuscripcion.buscarPorID(suscripcion_id)
        .orElseThrow(() -> new IllegalArgumentException("Suscripcion no encontrada"));


    model.put("suscripcion", suscripcion);
    renderWithUser(context, "suscripcion/detalle_suscripcion.hbs", model);
  }

  @Override
  public void create(Context context) {
    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();
    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladeras);

    renderWithUser(context, "suscripcion/formulario_suscripcion.hbs", model);
  }

  private Suscripcion getNuevaSuscripcion(Context context) {
    Suscripcion nuevaSuscripcion = new Suscripcion();

    String heladera_id_str = context.formParam("suscripcion_heladera_id");
    String tipo_suscripcion_str = context.formParam("tipo_suscripcion_id");
    String numero_viandas_str = context.formParam("numero_viandas");

    Long heladera_id = Long.valueOf(heladera_id_str);
    Heladera heladera = this.repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrada"));

    if (tipo_suscripcion_str.equals("pv")) {
      PocasViandas pocasViandas = new PocasViandas(Integer.valueOf(numero_viandas_str));
      nuevaSuscripcion.setTipoSuscripcion(pocasViandas);
      repositorioTipoSuscripcion.guardar(pocasViandas);
    } else if (tipo_suscripcion_str.equals("mv")) {
      MuchasViandas muchasViandas = new MuchasViandas(Integer.valueOf(numero_viandas_str));
      nuevaSuscripcion.setTipoSuscripcion(muchasViandas);
      repositorioTipoSuscripcion.guardar(muchasViandas);
    } else if (tipo_suscripcion_str.equals("dh")) {
      DesperfectoHeladera desperfectoHeladera = new DesperfectoHeladera();
      nuevaSuscripcion.setTipoSuscripcion(desperfectoHeladera);
      repositorioTipoSuscripcion.guardar(desperfectoHeladera);
    }

    Usuario usuario = getSessionUser(context);

    nuevaSuscripcion.setColaborador(usuario.getColaborador());
    nuevaSuscripcion.setHeladera(heladera);
    return nuevaSuscripcion;
  }


  @Override
  public void save(Context context) {
    Suscripcion nuevaSuscripcion = getNuevaSuscripcion(context);

    this.repositorioSuscripcion.guardar(nuevaSuscripcion);
    context.redirect("/suscripcion");
  }

  @Override
  public void edit(Context context) {
    Optional<Suscripcion> posibleSuscripcionBuscada = this.repositorioSuscripcion.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleSuscripcionBuscada.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("suscripcion", posibleSuscripcionBuscada.get());
    model.put("edicion", true);

    renderWithUser(context, "suscripcion/detalle_suscripcion.hbs", model);
  }

  @Override
  public void update(Context context) {
    Suscripcion nuevaSuscripcion = getNuevaSuscripcion(context);

    this.repositorioSuscripcion.modificar(nuevaSuscripcion);
    context.redirect("/suscripcion");
  }

  @Override
  public void delete(Context context) {
    Optional<Suscripcion> posibleSuscripcionBuscada = this.repositorioSuscripcion.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleSuscripcionBuscada.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("suscripcion", posibleSuscripcionBuscada.get());
    this.repositorioSuscripcion.eliminar(posibleSuscripcionBuscada.get());
    context.redirect("/suscripcion");
  }
}
