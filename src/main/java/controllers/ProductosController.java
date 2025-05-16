package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.entities.CalculadoraDePuntos.CalculadoraDePuntos;
import model.entities.Colaborador.Colaborador;
import model.entities.Producto.Producto;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioDeProductos;
import model.repositories.RepositorioDeRubros;
import model.repositories.RepositorioUsuario;

public class ProductosController extends BaseController {
  private final RepositorioDeProductos repositorioDeProductos;
  private final RepositorioDeRubros repositorioDeRubros;
  private final RepositorioColaborador repositorioColaborador;

  public ProductosController(RepositorioDeProductos repositorioDeProductos, RepositorioDeRubros repositorioDeRubros, RepositorioUsuario repositorioUsuario, RepositorioColaborador repositorioColaborador) {
    super(repositorioUsuario);
    this.repositorioDeProductos = repositorioDeProductos;
    this.repositorioDeRubros = repositorioDeRubros;
    this.repositorioColaborador = repositorioColaborador;
  }

  @Override
  public void index(Context context) {
    Usuario usuario = getSessionUser(context);

    List<Producto> productos = this.repositorioDeProductos.buscarProductosSinComprador();
    Colaborador comprador = usuario.getColaborador();

    Integer conseguidos = comprador.getPuntosTotales();
    Integer gastados = comprador.getPuntosGastados();
    Integer totales = conseguidos - gastados;

    Map<String, Object> model = new HashMap<>();
    model.put("productos", productos);
    model.put("puntos_colaborador", totales);
    model.put("titulo", "Listado de productos");

    renderWithUser(context, "productos/productos.hbs", model);
  }

  public void showOwned(Context context) {
    // NADA
  }

  @Override
  public void show(Context context) {
    Usuario usuario = getSessionUser(context);
    Colaborador comprador = usuario.getColaborador();
    Optional<Producto> posibleProductoBuscado = this.repositorioDeProductos.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleProductoBuscado.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Boolean comprado = comprador.getProductosComprados().contains(posibleProductoBuscado.get());

    Integer conseguidos = comprador.getPuntosTotales();
    Integer gastados = comprador.getPuntosGastados();
    Integer totales = conseguidos - gastados;
    Boolean disabled = posibleProductoBuscado.get().getCantidadDePuntos() > totales;

    Map<String, Object> model = new HashMap<>();
    model.put("producto", posibleProductoBuscado.get());
    model.put("puntos_colaborador", totales);
    model.put("disabled", disabled);
    model.put("comprado", comprado);
    renderWithUser(context, "productos/detalle_producto.hbs", model);
  }

  @Override
  public void create(Context context) {
    context.render("productos/formulario_producto.hbs");
  }

  @Override
  public void save(Context context) {
    Producto nuevoProducto = new Producto();

    nuevoProducto.setNombre(context.formParam("nombre"));
    nuevoProducto.setRubro(this.repositorioDeRubros.buscarPorID(Long.valueOf(context.formParam("rubro_id"))).get());
    nuevoProducto.setCantidadDePuntos(Integer.valueOf(context.formParam("precio")));
    nuevoProducto.setImagenIlustrativa(context.formParam("imagenIlustrativa"));

    this.repositorioDeProductos.guardar(nuevoProducto);
    context.redirect("/productos");
  }

  @Override
  public void edit(Context context) {
    Optional<Producto> posibleProductoBuscado = this.repositorioDeProductos.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleProductoBuscado.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("producto", posibleProductoBuscado.get());
    model.put("edicion", true);

    context.render("productos/detalle_producto.hbs", model);
  }

  @Override
  public void update(Context context) {

  }

  @Override
  public void delete(Context context) {

  }

  public void buy(Context context) throws IOException {
    String productoId = context.pathParam("id");
    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();

    Map<String, Object> response = new HashMap<>();
    Optional<Producto> posibleProductoBuscado = this.repositorioDeProductos.buscarPorID(Long.valueOf(productoId));

    if (posibleProductoBuscado.isEmpty()) {
      context.status(404).json(Map.of(
          "success", false,
          "mensaje", "Producto no encontrado"
      ));
      return;
    }

    Producto producto = posibleProductoBuscado.get();
    Integer puntosDisponibles = colaborador.getPuntosTotales() - colaborador.getPuntosGastados();

    if (puntosDisponibles >= producto.getCantidadDePuntos()) {
      colaborador.agregarProducto(producto);

      // Actualiza los puntos gastados
      CalculadoraDePuntos calculadoraDePuntos = new CalculadoraDePuntos();
      Integer nuevosGastados = (int) Math.ceil(calculadoraDePuntos.calcularPuntosGastados(colaborador));
      colaborador.setPuntosGastados(nuevosGastados);

      repositorioColaborador.modificar(colaborador);

      context.json(Map.of("success", true, "redirectUrl", "/tienda"));

    } else {
      context.status(400).json(Map.of("success", false, "message", "Puntos insuficientes para realizar el canje"));
    }
  }

  public void allProductsOwned(Context context) {
    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    List<Producto> productos = this.repositorioDeProductos.buscarProductosPorColaborador(colaborador);

    Map<String, Object> model = new HashMap<>();

    model.put("productos", productos);

    renderWithUser(context, "productos/productos_comprados.hbs", model);
  }
}
