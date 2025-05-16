package controllers;

import io.javalin.http.Context;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.entities.Heladera.Heladera;
import model.entities.Vianda.Comida;
import model.entities.Vianda.EstadoVianda;
import model.entities.Vianda.Vianda;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioComida;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioUsuario;
import model.repositories.RepositorioViandas;

public class ViandaController extends BaseController {

  RepositorioViandas repositorioViandas;
  RepositorioComida repositorioComida;

  RepositorioHeladera repositorioHeladera;

  RepositorioColaborador repositorioColaborador;

  public ViandaController(RepositorioViandas repositorioViandas, RepositorioComida repositorioComida, RepositorioHeladera repositorioHeladera, RepositorioColaborador repositorioColaborador, RepositorioUsuario repositorioUsuario) {
    super(repositorioUsuario);
    this.repositorioViandas = repositorioViandas;
    this.repositorioComida = repositorioComida;
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioColaborador = repositorioColaborador;
  }

  @Override
  public void index(Context context) {
    List<Vianda> viandas = this.repositorioViandas.buscarTodos();
    Map<String, Object> model = new HashMap<>();

    model.put("viandas", viandas);
    model.put("titulo", "Viandas");

    renderWithUser(context, "viandas/viandas.hbs", model);
  }

  public void detalleVianda(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long vianda_id = Long.valueOf(context.pathParam("id"));
    Vianda vianda = repositorioViandas.buscarPorId(vianda_id).
        orElseThrow(() -> new IllegalArgumentException("Vianda no encontrada"));

    model.put("vianda", vianda);


    renderWithUser(context, "viandas/detalle_vianda.hbs", model);
  }

  public void formEdicionVianda(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long vianda_id = Long.valueOf(context.pathParam("id"));
    Vianda vianda = repositorioViandas.buscarPorId(vianda_id).
        orElseThrow(() -> new IllegalArgumentException("Vianda no encontrada"));

    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();

    model.put("heladeras", heladeras);
    model.put("vianda", vianda);

    renderWithUser(context, "viandas/editar_vianda.hbs", model);
  }


  public void marcarEntregada(Context context) {
    String id = context.pathParam("id"); // Suponiendo que recibes el ID de la vianda
    Vianda vianda = repositorioViandas.buscarPorId(Long.parseLong(id)).
        orElseThrow(() -> new IllegalArgumentException("Vianda no encontrada"));

    vianda.setEstadoVianda(EstadoVianda.Entregada);
    repositorioViandas.modificar(vianda);
    context.redirect("/viandas");
  }

  private Vianda getNuevoVianda(Context context) {
    Vianda nuevaVianda = new Vianda();

    String idParam = context.formParam("id");
    if (idParam != null && !idParam.isEmpty()) {
      nuevaVianda.setId(Long.valueOf(idParam));
    }

    String comida_str = Objects.requireNonNull(context.formParam("comida")).toUpperCase();
    String fecha_cad_str = context.formParam("fecha_cad");
    String heladera_id_str = context.formParam("heladera_id");
    String calorias_str = context.formParam("calorias");
    String peso_str = context.formParam("peso");
    String fecha_don_str = context.formParam("fecha_don");
    String entregado_str = context.formParam("entregado");


    Comida comida = repositorioComida.buscarPorNombre(comida_str);
    if (comida == null) {
      comida = new Comida();
      comida.setNombre(comida_str);
      repositorioComida.guardar(comida);
    }
    String comida_id = context.formParam("comida_id");
    if (comida_id != null && !comida_id.isEmpty()) {
      comida.setNombre(comida_str);
      repositorioComida.modificar(comida);
    }

    Long heladera_id = Long.valueOf(heladera_id_str);
    Heladera heladera = this.repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrada"));
    // TODO, ARREGLAR PARA LA MODIFICACION

    boolean isEntregado = "on".equals(entregado_str) || "true".equals(entregado_str);
    EstadoVianda estadoVianda = null;
    if (isEntregado) {
      estadoVianda = EstadoVianda.Entregada;
    } else {
      estadoVianda = EstadoVianda.NoEntregada;
    }

    nuevaVianda.setComida(comida);
    nuevaVianda.setFechaCaducidad(LocalDate.parse(fecha_cad_str));
    nuevaVianda.setHeladera(heladera);
    nuevaVianda.setCalorias(Double.valueOf(calorias_str));
    nuevaVianda.setPeso(Double.valueOf(peso_str));
    nuevaVianda.setFechaDonacion(LocalDate.parse(fecha_don_str));
    nuevaVianda.setEstadoVianda(estadoVianda);

    return nuevaVianda;
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {
    Vianda nuevoVianda = getNuevoVianda(context);

    this.repositorioViandas.guardar(nuevoVianda);
    context.redirect("/viandas");
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) throws IOException {
    Vianda nuevoVianda = getNuevoVianda(context);

    this.repositorioViandas.modificar(nuevoVianda);
    context.redirect("/viandas");
  }

  @Override
  public void delete(Context context) {
    Long vianda_id = Long.parseLong(context.pathParam("id"));
    Vianda vianda = repositorioViandas.buscarPorId(vianda_id)
        .orElseThrow(() -> new IllegalArgumentException("Vianda no encontrada"));

    Map<String, Object> model = new HashMap<>();
    model.put("vianda", vianda);
    this.repositorioViandas.eliminar(vianda);
    context.redirect("/viandas");
  }


}
