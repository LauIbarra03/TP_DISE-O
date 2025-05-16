package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import model.entities.Colaborador.Colaborador;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Ciudad;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;
import model.entities.Direccion.Provincia;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Heladera.Modelo;
import model.entities.Heladera.SolicitudSobreHeladera.SolicitudSobreHeladera;
import model.entities.Incidente.Incidente;
import model.entities.Incidente.TipoAlerta;
import model.entities.Incidente.TipoIncidente;
import model.entities.PuntoGeografico.PuntoGeografico;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.entities.Vianda.Vianda;
import model.repositories.RepositorioCalle;
import model.repositories.RepositorioCiudad;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioDeDirecciones;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioDeModelos;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioProvincia;
import model.repositories.RepositorioPuntoGeografico;
import model.repositories.RepositorioUsuario;
import model.repositories.RepositorioViandas;

public class HeladerasController extends BaseController {
  private RepositorioHeladera repositorioHeladera;
  private RepositorioProvincia repositorioProvincia;
  private RepositorioCiudad repositorioCiudad;
  private RepositorioLocalidad repositorioLocalidad;
  private RepositorioDeIncidentes repositorioDeIncidentes;
  private RepositorioDeModelos repositorioDeModelos;
  private RepositorioCalle repositorioCalle;
  private RepositorioDeDirecciones repositorioDeDirecciones;
  private RepositorioPuntoGeografico repositorioPuntoGeografico;
  private RepositorioViandas repositorioViandas;

  public HeladerasController(RepositorioHeladera repositorioHeladera, RepositorioUsuario repositorioUsuario,
                             RepositorioProvincia repositorioProvincia,
                             RepositorioCiudad repositorioCiudad, RepositorioLocalidad repositorioLocalidad,
                             RepositorioDeIncidentes repositorioDeIncidentes,
                             RepositorioColaborador repositorioColaborador, RepositorioCalle repositorioCalle,
                             RepositorioDeDirecciones repositorioDeDirecciones,
                             RepositorioPuntoGeografico repositorioPuntoGeografico, RepositorioViandas repositorioViandas,
                             RepositorioDeModelos repositorioDeModelos) {
    super(repositorioUsuario);
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioProvincia = repositorioProvincia;
    this.repositorioCiudad = repositorioCiudad;
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioDeIncidentes = repositorioDeIncidentes;
    this.repositorioCalle = repositorioCalle;
    this.repositorioDeDirecciones = repositorioDeDirecciones;
    this.repositorioPuntoGeografico = repositorioPuntoGeografico;
    this.repositorioViandas = repositorioViandas;
    this.repositorioDeModelos = repositorioDeModelos;

  }

  public HeladerasController(RepositorioHeladera repositorioHeladera) {
  }

  @Override
  public void index(Context context) {
    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladeras);
    model.put("titulo", "Heladeras");

    renderWithUser(context, "heladeras/heladeras.hbs", model);
  }

  public void mostrarFormularioEdicion(Context context) {
    Map<String, Object> model = new HashMap<>();

    agregarCiudadesProvinciasYLocalidades(model);

    Long heladera_id = Long.parseLong(context.pathParam("id"));
    Heladera heladera = repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Persona vulnerable no encontrada"));

    List<Map<String, String>> estadosHeladera = Arrays.stream(EstadoHeladera.values())
        .map(estado -> {
          Map<String, String> estadoMap = new HashMap<>();
          estadoMap.put("valor", estado.name()); // Valor en mayúsculas
          return estadoMap;
        })
        .collect(Collectors.toList());

    Direccion direccion = heladera.getPuntoGeografico().getDireccion();
    Float latitud = heladera.getPuntoGeografico().getLatitud();
    Float longintud = heladera.getPuntoGeografico().getLongitud();

    EstadoHeladera estadoHeladera = heladera.getEstadoHeladera();
    String estado = String.valueOf(estadoHeladera);

    model.put("heladera", heladera);
    model.put("direccion", direccion);
    model.put("latitud", latitud);
    model.put("longitud", longintud);
    model.put("estadosHeladera", estadosHeladera);
    model.put("estado", estado);
    model.put("titulo", "Formulario Heladera");

    renderWithUser(context, "heladeras/editar_heladera.hbs", model);
  }

  public void detalleHeladera(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long heladera_id = Long.parseLong(context.pathParam("id"));

    Heladera heladera = repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrado"));

    List<Vianda> viandas = repositorioViandas.buscarViandasPorHeladeraId(heladera_id);

    EstadoHeladera estadoHeladera = heladera.getEstadoHeladera();
    String estado = String.valueOf(estadoHeladera);

    Direccion direccion = heladera.getPuntoGeografico().getDireccion();
    Float latitud = heladera.getPuntoGeografico().getLatitud();
    Float logintud = heladera.getPuntoGeografico().getLongitud();

    String mensajeIncidente = "";
    if (heladera.getIncidentes().isEmpty() || heladera.getIncidentes().stream().allMatch(Incidente::getEstaResuelto)) {
      mensajeIncidente = "-";
    } else {
      for (Incidente incidente : heladera.getIncidentes()) {
        if (!incidente.getEstaResuelto()) {
          TipoIncidente tipoIncidente = incidente.getTipoIncidente();
          if (tipoIncidente == TipoIncidente.FallaTecnica) {
            mensajeIncidente = "Falla técnica, ";
          } else if (tipoIncidente == TipoIncidente.Alerta) {
            TipoAlerta tipoAlerta = incidente.getTipoAlerta();
            if (tipoAlerta == TipoAlerta.Fraude) {
              mensajeIncidente = "Fraude, ";
            } else if (tipoAlerta == TipoAlerta.FallaConexion) {
              mensajeIncidente = "Falla conexión, ";
            } else if (tipoAlerta == TipoAlerta.Temperatura) {
              mensajeIncidente = "Temperatura fuera de rango permitido, ";
            }
          }
        }
      }
      mensajeIncidente = mensajeIncidente.substring(0, mensajeIncidente.length() - 2);
    }

    Usuario usuario = getSessionUser(context);
    if (usuario.getTipoRol().equals(TipoRol.PERSONA_JURIDICA)) {
      List<Heladera> heladerasACargo = repositorioHeladera.heladerasACargo(usuario.getColaborador().getId());
      if (heladerasACargo.contains(heladera)) {
        String permisoEdicion = "true";
        model.put("permisoEdicion", permisoEdicion);
      }
    } else if (usuario.getTipoRol().equals(TipoRol.ADMIN)) {
      String permisoEdicion = "true";
      model.put("permisoEdicion", permisoEdicion);
    }

    model.put("heladera", heladera);
    model.put("estado", estado);
    model.put("latitud", latitud);
    model.put("longitud", logintud);
    model.put("direccion", direccion);
    model.put("viandas", viandas);
    model.put("mensajeIncidente", mensajeIncidente);

    renderWithUser(context, "heladeras/detalle_heladera.hbs", model);
  }

  @Override
  public void show(Context context) {
    Optional<Heladera> posibleHeladeraBuscada = this.repositorioHeladera
        .buscarPorID(Long.valueOf(context.pathParam("id")));
    List<Incidente> alertas = this.repositorioDeIncidentes.buscarAlertas(Long.valueOf(context.pathParam("id")));

    if (posibleHeladeraBuscada.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("heladera", posibleHeladeraBuscada.get());
    model.put("alertas", alertas);

    context.render("heladeras/detalle_heladera.hbs", model);
  }

  @Override
  public void create(Context context) {
    context.render("heladeras/formulario_heladera.hbs");
  }

  private Heladera getNuevaHeladera(Context context) {

    Heladera nuevaHeladera = new Heladera();

    String idParam = context.formParam("id");
    if (idParam != null && !idParam.isEmpty()) {
      nuevaHeladera.setId(Long.valueOf(idParam));
    }

    // ESTO VA ASI Y NO SE TOCA. NO ME IMPORTA LO QUE DIGAN. funciona uwu
    PuntoGeografico puntoGeografico = validarPuntoGeografico(context);

    nuevaHeladera.setPuntoGeografico(puntoGeografico);
    nuevaHeladera
        .setCapacidadViandas(Integer.valueOf(Objects.requireNonNull(context.formParam("capacidad_viandas"))));
    nuevaHeladera
        .setFechaDeInicio(LocalDate.parse(Objects.requireNonNull(context.formParam("fecha_funcionamiento"))));

    Modelo modelo = repositorioDeModelos.buscarPorID(Long.valueOf(context.formParam("modelo"))).get();
    modelo.setNombre(puntoGeografico.getDireccion().show());
    modelo.setTempMaxima(Integer.valueOf(context.formParam("temp_max")));
    modelo.setTempMinima(Integer.valueOf(context.formParam("temp_min")));
    repositorioDeModelos.modificar(modelo);

    nuevaHeladera.setModelo(modelo);
    nuevaHeladera.setEstadoHeladera2(EstadoHeladera.valueOf(context.formParam("estado_heladera")));

    return nuevaHeladera;
  }

  @Override
  public void save(Context context) {
    Heladera nuevaHeladera = getNuevaHeladera(context);

    this.repositorioHeladera.guardar(nuevaHeladera);
    context.redirect("/heladeras");
  }

  @Override
  public void edit(Context context) {
    Optional<Heladera> posibleHeladeraBuscada = this.repositorioHeladera
        .buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleHeladeraBuscada.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("colaborador", posibleHeladeraBuscada.get());
    model.put("edicion", true);

    context.render("colaborador/detalle_heladera.hbs", model);
  }

  @Override
  public void update(Context context) throws IOException {
    Heladera heladera = getNuevaHeladera(context);

    repositorioHeladera.modificar(heladera);
    // context.redirect("/heladeras");
  }

  @Override
  public void delete(Context context) {
    Long heladera_id = Long.parseLong(context.pathParam("id"));
    Heladera heladera = repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrada"));

    Map<String, Object> model = new HashMap<>();
    model.put("heladera", heladera);
    this.repositorioHeladera.eliminar(heladera);
    context.redirect("/heladeras");
  }

  public void obtenerCercanas(Context context) {
    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    Direccion direccionColaborador = colaborador.getDireccion();

    List<Heladera> heladeras = this.repositorioHeladera.buscarCercanas(direccionColaborador);

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladeras);
    model.put("titulo", "Heladeras");

    context.render("heladeras/heladeras.hbs", model);
  }

  /*****************************************************
   * AUXILIARES
   *****************************************************/
  public void agregarCiudadesProvinciasYLocalidades(Map<String, Object> model) {
    List<Ciudad> ciudades = this.repositorioCiudad.buscarTodos();
    List<Localidad> localidades = this.repositorioLocalidad.buscarTodos();
    List<Provincia> provincias = this.repositorioProvincia.buscarTodos();

    model.put("ciudades", ciudades);
    model.put("localidades", localidades);
    model.put("provincias", provincias);
  }

  public PuntoGeografico validarPuntoGeografico(Context context) {
    Direccion direccion = repositorioDeDirecciones.buscarPorId(Long.valueOf(context.formParam("direccion_id")))
        .orElseThrow(() -> new IllegalArgumentException("Direccion no encontrada"));
    PuntoGeografico puntoGeografico = repositorioPuntoGeografico
        .buscarPorId(Long.valueOf(context.formParam("puntoGeografico_id")))
        .orElseThrow(() -> new IllegalArgumentException("Punto geografico no encontrado"));

    List<Calle> calles = repositorioCalle.buscarPorNombre(context.formParam("calle"));
    Calle calle = calles.isEmpty() ? null : calles.get(0);

    if (calle == null) {
      calle = new Calle();
      calle.setNombre(context.formParam("calle"));
      repositorioCalle.guardar(calle);
    }

    Integer altura = Integer.valueOf(Objects.requireNonNull(context.formParam("altura")));
    String codigoPostal = Objects.requireNonNull(context.formParam("codigoPostal"));

    List<Ciudad> ciudades = repositorioCiudad.buscarPorNombre(context.formParam("ciudad"));
    Ciudad ciudad = ciudades.isEmpty() ? null : ciudades.get(0);

    if (ciudad == null) {
      ciudad = new Ciudad();
      ciudad.setNombre(context.formParam("ciudad"));
      repositorioCiudad.guardar(ciudad);
    }

    List<Localidad> localidades = repositorioLocalidad.buscarPorNombre(context.formParam("localidad"), context.formParam("provincia"));
    Localidad localidad = localidades.isEmpty() ? null : localidades.get(0);

    if (localidad == null) {
      localidad = new Localidad();
      localidad.setNombre(context.formParam("localidad"));
      localidad.setProvincia(new Provincia(context.formParam("provincia")));
      repositorioLocalidad.guardar(localidad);
    }

    direccion.setCalle(calle);
    direccion.setAltura(altura);
    direccion.setCodigoPostal(codigoPostal);
    direccion.setCiudad(ciudad);
    direccion.setLocalidad(localidad);

    repositorioDeDirecciones.modificar(direccion);

    String nombre_pg = direccion.getCalle().getNombre() + " " + direccion.getAltura() + ", "
        + direccion.getCodigoPostal();
    puntoGeografico.setLatitud(Float.valueOf(context.formParam("latitud")));
    puntoGeografico.setLongitud(Float.valueOf(context.formParam("longitud")));
    puntoGeografico.setDireccion(direccion);
    puntoGeografico.setNombre(nombre_pg);

    repositorioPuntoGeografico.modificar(puntoGeografico);

    return puntoGeografico;
  }

  public void mostrarMapa(Context context) {
    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();
    List<Map<String, Object>> heladerasInfo = new ArrayList<>();

    for (Heladera heladera : heladeras) {
      Map<String, Object> heladeraData = new HashMap<>();

      // Obtener la información de cada heladera
      Direccion direccion = heladera.getPuntoGeografico().getDireccion();
      Float latitud = heladera.getPuntoGeografico().getLatitud();
      Float longitud = heladera.getPuntoGeografico().getLongitud();

      // Agregar la información al mapa
      heladeraData.put("id", heladera.getId());
      heladeraData.put("direccion", direccion);
      heladeraData.put("latitud", latitud);
      heladeraData.put("longitud", longitud);
      heladeraData.put("estadoHeladera", heladera.getEstadoHeladera());

      // Agregar la información de esta heladera a la lista
      heladerasInfo.add(heladeraData);
    }

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladerasInfo);
    model.put("titulo", "Mapa de heladeras");

    renderWithUser(context, "heladeras/mapa-heladeras.hbs", model);
  }

  public void mostrarFormularioApertura(Context context) {
    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();

    Map<String, Object> model = new HashMap<>();
    model.put("heladeras", heladeras);
    model.put("titulo", "Solicitud de Apertura");

    renderWithUser(context, "solicitudApertura/formulario_solicitudApertura.hbs", model);
  }

  public void saveSolicitudApertura(Context context) {
    Heladera heladera = repositorioHeladera.buscarPorID(Long.valueOf(context.formParam("heladera_id"))).get();
    Long user_id = context.sessionAttribute("user_id");
    Colaborador colaborador = repositorioUsuario.buscarPorID(user_id).get().getColaborador();
    SolicitudSobreHeladera nuevaSoli = new SolicitudSobreHeladera();
    nuevaSoli.setHeladera(heladera);
    nuevaSoli.setFechaYHora(LocalDateTime.now());
    nuevaSoli.setTarjetaColaborador(colaborador.getTarjeta());
    heladera.agregarSolicitud(nuevaSoli);

    repositorioHeladera.modificar(heladera);

    context.redirect("/solicitudApertura/formulario_solicitudApertura");
  }
}
