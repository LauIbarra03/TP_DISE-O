package controllers;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import model.dtos.ViandaDTO;
import model.entities.CalculadoraDePuntos.CalculadoraDePuntos;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.FormaDeContribucion;
import model.entities.Contribucion.Contribucion;
import model.entities.Contribucion.DistribucionVianda.DistribucionViandas;
import model.entities.Contribucion.DonacionDinero.DonacionDinero;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;
import model.entities.Contribucion.HacerseCargoHeladera.HacerseCargoHeladera;
import model.entities.Contribucion.RealizarOfertas.RealizarOfertas;
import model.entities.Contribucion.RegistroPersonaEnSituacionVulnerable.RegistroPersonaVulnerable;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Ciudad;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;
import model.entities.Direccion.Provincia;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Heladera.Modelo;
import model.entities.Heladera.RegistroHeladera;
import model.entities.PersonaVulnerable.PersonaVulnerable;
import model.entities.Producto.Producto;
import model.entities.Producto.Rubro;
import model.entities.PuntoGeografico.PuntoGeografico;
import model.entities.Tarjeta.Tarjeta;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.entities.Utils.TipoDocumento;
import model.entities.Vianda.Comida;
import model.entities.Vianda.EstadoVianda;
import model.entities.Vianda.Vianda;
import model.repositories.RepositorioCalle;
import model.repositories.RepositorioCiudad;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioComida;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioDeDirecciones;
import model.repositories.RepositorioDeModelos;
import model.repositories.RepositorioDeProductos;
import model.repositories.RepositorioDeRubros;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioPersonaVulnerable;
import model.repositories.RepositorioProvincia;
import model.repositories.RepositorioPuntoGeografico;
import model.repositories.RepositorioRegistroActividadHeladera;
import model.repositories.RepositorioTarjeta;
import model.repositories.RepositorioUsuario;
import model.repositories.RepositorioViandas;
import utils.JsonHelper;

public class ContribucionesController extends BaseController {
  CalculadoraDePuntos calculadoraDePuntos = new CalculadoraDePuntos();
  private RepositorioContribucion repositorioContribucion;
  private RepositorioHeladera repositorioHeladera;
  private RepositorioPersonaVulnerable repositorioPersonaVulnerable;
  private RepositorioTarjeta repositorioTarjeta;
  private RepositorioDeProductos repositorioDeProductos;
  private RepositorioDeRubros repositorioDeRubros;
  private RepositorioProvincia repositorioProvincia;
  private RepositorioCiudad repositorioCiudad;
  private RepositorioLocalidad repositorioLocalidad;
  private RepositorioCalle repositorioCalle;
  private RepositorioColaborador repositorioColaborador;
  private RepositorioComida repositorioComida;
  private RepositorioViandas repositorioViandas;
  private RepositorioPuntoGeografico repositorioPuntoGeografico;
  private RepositorioDeDirecciones repositorioDeDirecciones;
  private RepositorioDeModelos repositorioDeModelos;
  private RepositorioRegistroActividadHeladera repositorioRegistroActividadHeladera;

  public ContribucionesController(RepositorioUsuario repositorioUsuario, RepositorioContribucion repositorioContribucion, RepositorioHeladera repositorioHeladera,
                                  RepositorioPersonaVulnerable repositorioPersonaVulnerable, RepositorioTarjeta repositorioTarjeta,
                                  RepositorioProvincia repositorioProvincia, RepositorioCiudad repositorioCiudad, RepositorioLocalidad repositorioLocalidad,
                                  RepositorioCalle repositorioCalle, RepositorioColaborador repositorioColaborador, RepositorioDeRubros repositorioDeRubros,
                                  RepositorioComida repositorioComida, RepositorioViandas repositorioViandas, RepositorioDeProductos repositorioDeProductos,
                                  RepositorioPuntoGeografico repositorioPuntoGeografico, RepositorioDeDirecciones repositorioDeDirecciones,
                                  RepositorioDeModelos repositorioDeModelos, RepositorioRegistroActividadHeladera repositorioRegistroActividadHeladera) {
    super(repositorioUsuario);
    this.repositorioContribucion = repositorioContribucion;
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    this.repositorioTarjeta = repositorioTarjeta;
    this.repositorioProvincia = repositorioProvincia;
    this.repositorioCiudad = repositorioCiudad;
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioCalle = repositorioCalle;
    this.repositorioColaborador = repositorioColaborador;
    this.repositorioDeRubros = repositorioDeRubros;
    this.repositorioComida = repositorioComida;
    this.repositorioViandas = repositorioViandas;
    this.repositorioDeProductos = repositorioDeProductos;
    this.repositorioPuntoGeografico = repositorioPuntoGeografico;
    this.repositorioDeDirecciones = repositorioDeDirecciones;
    this.repositorioDeModelos = repositorioDeModelos;
    this.repositorioRegistroActividadHeladera = repositorioRegistroActividadHeladera;
  }

  public ContribucionesController(RepositorioContribucion repositorioContribucion) {
    super();
  }

  public void listaColaboracion(Context context) {
    Map<String, Object> model = new HashMap<>();
    Usuario usuario = getSessionUser(context);
    List<Contribucion> contribuciones = new ArrayList<>();

    if (usuario.getTipoRol() == TipoRol.ADMIN) {
      contribuciones = this.repositorioContribucion.buscarTodos();
    } else {
      Long colaborador_id = usuario.getColaborador().getId();
      contribuciones = this.repositorioContribucion.buscarContribucionesPorColaborador(colaborador_id);
    }


    model.put("contribuciones", contribuciones);


    renderWithUser(context, "colaboraciones/lista_colaboraciones.hbs", model);
  }

  public void index(Context context) {

    Usuario usuario = getSessionUser(context);
    Long colaboradorId = usuario.getColaborador().getId();

    Colaborador colaborador = repositorioColaborador.buscarPorID(colaboradorId)
        .orElseThrow(() -> new IllegalArgumentException("Colaborador no encontrado"));

    List<Heladera> heladeras = this.repositorioHeladera.findAllHeladerasWithDireccion();
    List<Tarjeta> tarjetas = this.repositorioTarjeta.buscarTodos();
    Map<String, Object> model = new HashMap<>();
    model.put("colaborador", colaborador);
    model.put("titulo", "Formas de contribuir");
    model.put("heladeras", heladeras);
    model.put("tarjetas", tarjetas);

    renderWithUser(context, "colaboraciones/colaboraciones.hbs", model);
  }

  public void detalleColaboracion(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long colaboracion_id = Long.parseLong(context.pathParam("id"));

    Contribucion contribucion = repositorioContribucion.buscarPorID(colaboracion_id)
        .orElseThrow(() -> new IllegalArgumentException("Colaboración no encontrado"));

    model.put("contribucion", contribucion);
    renderWithUser(context, "colaboraciones/detalle_colaboracion.hbs", model);
  }


  /***************************************************** DONACION DE VIANDA *****************************************************/
  public void formDonacionVianda(Context context) {

    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();
    Map<String, Object> model = new HashMap<>();

    model.put("heladeras", heladeras);

    renderWithUser(context, "colaboraciones/donacion_vianda.hbs", model);
  }


  public void saveDonacionVianda(Context context) throws IOException {
    Vianda nuevaVianda = new Vianda();
    String comida_str = Objects.requireNonNull(context.formParam("comida")).toLowerCase();
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

    Long heladera_id = Long.valueOf(heladera_id_str);
    Heladera heladera = this.repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrada"));
    heladera.agregarViandas(1);

    boolean isEntregado = ("on".equals(entregado_str) || "true".equals(entregado_str));
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

    DonacionVianda donacionVianda = new DonacionVianda();
    donacionVianda.setNuevaVianda(nuevaVianda);

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();

    List<Vianda> viandas = new ArrayList<>();
    viandas.add(nuevaVianda);

    donacionVianda.setColaborador(colaborador);
    donacionVianda.setViandas(viandas);
    nuevaVianda.setColaborador(colaborador);


    this.repositorioViandas.guardar(nuevaVianda);
    this.repositorioContribucion.guardar(donacionVianda);
    this.repositorioHeladera.modificar(heladera);

    actualizarPuntos(colaborador, donacionVianda);

    context.redirect("/viandas");
  }


  /***************************************************** DONACION DE DINERO *****************************************************/
  public void formDonacionDinero(Context context) {

    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Donacion de dinero");


    renderWithUser(context, "colaboraciones/donacion_dinero.hbs", model);
  }

  public void editarDonacionDinero(Context context) {
    Map<String, Object> model = new HashMap<>();

    model.put("titulo", "Editar Donacion de dinero");

    Long colaboracion_id = Long.parseLong(context.pathParam("id"));
    Contribucion contribucion = repositorioContribucion.buscarPorID(colaboracion_id)
        .orElseThrow(() -> new IllegalArgumentException("Colaboración no encontrado"));
    model.put("contribucion", contribucion);

    renderWithUser(context, "colaboraciones/editar_donacion_dinero.hbs", model);
  }

  public void updateDonacionDinero(Context context) {
    DonacionDinero nuevaDonacionDinero = new DonacionDinero();

    String idParam = context.formParam("id");
    if (idParam != null && !idParam.isEmpty()) {
      nuevaDonacionDinero.setId(Long.valueOf(idParam));
    }

    String donacion_cant_dinero_str = context.formParam("donacion_cant_dinero");
    String fecha_donacion_str = context.formParam("fecha_donacion");
    String donar_frecuencia_str = context.formParam("donar_frecuencia");
    String frecuencia_str = context.formParam("frecuencia");

    if ("si".equals(donar_frecuencia_str)) {
      nuevaDonacionDinero.setFrecuencia(Integer.parseInt(frecuencia_str));
    }

    nuevaDonacionDinero.setCantidadDinero(Integer.parseInt(donacion_cant_dinero_str));

    LocalDate fecha = LocalDate.parse(fecha_donacion_str);
    LocalDateTime fechaHora = fecha.atStartOfDay();
    nuevaDonacionDinero.setFechaHora(fechaHora);
    // nuevaDonacionDinero.setColaborador(this.repositorioUsuario.buscarPorID(Long.valueOf(context.sessionAttribute("user_id"))).get().getColaborador());

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    nuevaDonacionDinero.setColaborador(colaborador);


    this.repositorioContribucion.modificar(nuevaDonacionDinero);

    context.redirect("/colaboraciones");
  }

  public void saveDonacionDinero(Context context) {
    DonacionDinero nuevaDonacionDinero = new DonacionDinero();
//        if(!tieneFormaContribucion(context, FormaDeContribucion.DonacionDinero)) {
//            context.status(HttpStatus.METHOD_NOT_ALLOWED);
//            return;
//        }
    String donacion_cant_dinero_str = context.formParam("donacion_cant_dinero");
    String fecha_donacion_str = context.formParam("fecha_donacion");
    String donar_frecuencia_str = context.formParam("donar_frecuencia");
    String frecuencia_str = context.formParam("frecuencia");

    if ("si".equals(donar_frecuencia_str)) {
      nuevaDonacionDinero.setFrecuencia(Integer.parseInt(frecuencia_str));
    }

    nuevaDonacionDinero.setCantidadDinero(Integer.parseInt(donacion_cant_dinero_str));

    LocalDate fecha = LocalDate.parse(fecha_donacion_str);
    LocalDateTime fechaHora = fecha.atStartOfDay();
    nuevaDonacionDinero.setFechaHora(fechaHora);
    // nuevaDonacionDinero.setColaborador(this.repositorioUsuario.buscarPorID(Long.valueOf(context.sessionAttribute("user_id"))).get().getColaborador());

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    nuevaDonacionDinero.setColaborador(colaborador);

    this.repositorioContribucion.guardar(nuevaDonacionDinero);
    actualizarPuntos(colaborador, nuevaDonacionDinero);
    context.redirect("/colaboraciones");
  }


  /***************************************************** DISTRIBUCION DE VIANDAS *****************************************************/
  public void formDistribucionViandas(Context context) throws IOException {
    JsonHelper jsonHelper = new JsonHelper();
    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();
    List<ViandaDTO> viandasDTO = this.repositorioViandas.buscarTodos()
        .stream()
        .map(ViandaDTO::new)
        .toList();

    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Distribución de viandas");
    model.put("heladeras", heladeras);
    model.put("viandas", viandasDTO);

    renderWithUser(context, "colaboraciones/distribucion_viandas.hbs", model);
  }

  public void saveDistribuirViandas(Context context) throws IOException {
//        if(!tieneFormaContribucion(context, FormaDeContribucion.DistribucionDeViandas)) {
//            context.status(HttpStatus.METHOD_NOT_ALLOWED);
//            return;
//        }
    DistribucionViandas distribucionViandas = new DistribucionViandas();
    String heladeraOrigenId = context.formParam("heladera_origen");
    String heladeraDestinoId = context.formParam("heladera_destino");
    String motivoDistribucion = context.formParam("motivo_distribucion");
    String fechaDistribucionStr = context.formParam("fecha_distribucion");
    List<String> viandas_a_dsitribuir = context.formParams("viandas[]");
    System.out.println("******************************************* " + viandas_a_dsitribuir + " ******************************************* ");
    if (heladeraOrigenId == null || heladeraDestinoId == null || motivoDistribucion == null || fechaDistribucionStr == null) {
      context.status(400).result("Faltan datos en el formulario");
      return;
    }

    if (viandas_a_dsitribuir.isEmpty()) {
      context.status(400).result("No se han seleccionado viandas.");
      return;
    }
    Long origenId = Long.parseLong(heladeraOrigenId);
    Long destinoId = Long.parseLong(heladeraDestinoId);
    LocalDate fechaDistribucion = LocalDate.parse(fechaDistribucionStr);

    Heladera origen = this.repositorioHeladera.buscarPorID(origenId)
        .orElseThrow(() -> new IllegalArgumentException("Heladera origen no encontrada"));


    Heladera destino = this.repositorioHeladera.buscarPorID(destinoId)
        .orElseThrow(() -> new IllegalArgumentException("Heladera destino no encontrada"));


    List<Vianda> viandasParaDistribuir = new ArrayList<>();
    for (String viandaIdStr : viandas_a_dsitribuir) {
      Long viandaId = Long.parseLong(viandaIdStr);
      Vianda vianda = this.repositorioViandas.buscarPorId(viandaId)
          .orElseThrow(() -> new IllegalArgumentException("Vianda no encontrada: " + viandaId));

      vianda.setHeladera(destino);
      this.repositorioViandas.modificar(vianda);
      viandasParaDistribuir.add(vianda);
    }

    distribucionViandas.setViandas(viandasParaDistribuir);
    Integer cantidadViandas = viandasParaDistribuir.size();
    destino.agregarViandas(cantidadViandas);
    origen.sacarViandas(cantidadViandas);
    LocalDateTime fechaHora = fechaDistribucion.atStartOfDay();

    distribucionViandas.setHeladeraOrigen(origen);
    distribucionViandas.setHeladeraDestino(destino);
    distribucionViandas.setMotivo(motivoDistribucion);
    distribucionViandas.setFechaHora(fechaHora);
    distribucionViandas.setCantidadViandas(cantidadViandas);

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    distribucionViandas.setColaborador(colaborador);

    this.repositorioContribucion.guardar(distribucionViandas);
    this.repositorioHeladera.modificar(origen);
    this.repositorioHeladera.modificar(destino);
    actualizarPuntos(colaborador, distribucionViandas);
    context.redirect("/colaboraciones");
  }

  /***************************************************** HACERSE CARGO HELADERA *****************************************************/
  public void editarHacerseCargoHeladera(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long colaboracion_id = Long.parseLong(context.pathParam("id"));
    Contribucion contribucion = repositorioContribucion.buscarPorID(colaboracion_id)
        .orElseThrow(() -> new IllegalArgumentException("Colaboración no encontrado"));

    Heladera heladera = contribucion.getNuevaHeladera();
    List<Modelo> modelos = this.repositorioDeModelos.buscarTodos();

    agregarCiudadesProvinciasYLocalidades(model);

    List<Map<String, String>> estadosHeladera = Arrays.stream(EstadoHeladera.values())
        .map(estado -> {
          Map<String, String> estadoMap = new HashMap<>();
          estadoMap.put("valor", estado.name()); // Valor en mayúsculas
          return estadoMap;
        })
        .collect(Collectors.toList());


    model.put("estadosHeladera", estadosHeladera);
    model.put("contribucion", contribucion);
    model.put("heladera", heladera);
    model.put("modelos", modelos);

    renderWithUser(context, "colaboraciones/editar_hacerse_cargo_heladera.hbs", model);
  }

  // TODO, NO FUNCIONA
//  public void updateHacerseCargoHeladera(Context context) {
//    Heladera nuevaHeladera = new Heladera();
//
//    Calle calle = new Calle();
//    calle.setNombre(context.formParam("calle"));
//    String calle_id = context.formParam("calle_id");
//
//    if (calle_id != null && !calle_id.isEmpty()) {
//      calle.setId(Long.valueOf(calle_id));
//      this.repositorioCalle.modificar(calle);
//    } else {
//
//      this.repositorioCalle.guardar(calle);
//    }
//    Integer altura = Integer.valueOf(Objects.requireNonNull(context.formParam("altura")));
//    String codigoPostal = Objects.requireNonNull(context.formParam("codigoPostal"));
//
//
//    String ciudadIdParam = context.formParam("ciudad_id");
//    String provinciaParam = context.formParam("provincia_id");
//    String localidadParam = context.formParam("localidad_id");
//
//    Long ciudadId = Long.valueOf(ciudadIdParam);
//    Long provinciaId = Long.valueOf(provinciaParam);
//    Long localidadId = Long.valueOf(localidadParam);
//
//    Ciudad ciudad = this.repositorioCiudad.buscarPorID(ciudadId)
//        .orElseThrow(() -> new IllegalArgumentException("Ciudad no encontrada"));
//    Provincia provincia = this.repositorioProvincia.buscarPorID(provinciaId)
//        .orElseThrow(() -> new IllegalArgumentException("Provincia no encontrada"));
//    Localidad localidad = this.repositorioLocalidad.buscarPorID(localidadId)
//        .orElseThrow(() -> new IllegalArgumentException("Localidad no encontrada"));
//
//
//    Direccion direccion = new Direccion();
//    direccion.setCalle(calle);
//    direccion.setAltura(altura);
//    direccion.setCodigoPostal(codigoPostal);
//    direccion.setCiudad(ciudad);
//    localidad.setProvincia(provincia);
//    direccion.setLocalidad(localidad);
//
//    String direccion_id = context.formParam("direccion_id");
//    if (direccion_id != null && !direccion_id.isEmpty()) {
//      direccion.setId(Long.valueOf(direccion_id));
//      this.repositorioDeDirecciones.modificar(direccion);
//    } else {
//      this.repositorioDeDirecciones.guardar(direccion);
//    }
//
//    String nombre_pg = direccion.getCalle().getNombre() + " " + direccion.getAltura() + ", " + direccion.getCodigoPostal();
//    PuntoGeografico puntoGeografico = new PuntoGeografico(
//        1.0f,
//        1.0f,
//        direccion,
//        nombre_pg
//    );
//    String puntroGeografico_id = context.formParam("direccion_id");
//    if (puntroGeografico_id != null && !puntroGeografico_id.isEmpty()) {
//      puntoGeografico.setId(Long.valueOf(puntroGeografico_id));
//      this.repositorioPuntoGeografico.modificar(puntoGeografico);
//    } else {
//      this.repositorioDeDirecciones.guardar(direccion);
//    }
//
//    String estadoString = context.formParam("estado_heladera");
//
//
//    nuevaHeladera.setPuntoGeografico(puntoGeografico);
//    nuevaHeladera.setCapacidadViandas(Integer.valueOf(Objects.requireNonNull(context.formParam("capacidad_viandas"))));
//    nuevaHeladera.setFechaDeInicio(LocalDate.parse(Objects.requireNonNull(context.formParam("fecha_funcionamiento"))));
//    nuevaHeladera.setModelo(repositorioDeModelos.buscarPorID(Long.valueOf(context.formParam("modelo"))).get());
//    nuevaHeladera.setEstadoHeladera2(EstadoHeladera.valueOf(estadoString));
//
//
//    String idParam = context.formParam("id");
//    if (idParam != null && !idParam.isEmpty()) {
//      nuevaHeladera.setId(Long.valueOf(idParam));
//      this.repositorioHeladera.guardar(nuevaHeladera);
//    } else {
//      this.repositorioHeladera.modificar(nuevaHeladera);
//    }
//    HacerseCargoHeladera hacerseCargoHeladera = new HacerseCargoHeladera();
//    hacerseCargoHeladera.setNuevaHeladera(nuevaHeladera);
//
//    Usuario usuario = getSessionUser(context);
//    hacerseCargoHeladera.setColaborador(usuario.getColaborador());
//
//    String contribucion_id = context.formParam("contribucion_id");
//    if (contribucion_id != null && !contribucion_id.isEmpty()) {
//      hacerseCargoHeladera.setId(Long.valueOf(contribucion_id));
//      this.repositorioContribucion.modificar(hacerseCargoHeladera);
//    } else {
//      this.repositorioContribucion.guardar(hacerseCargoHeladera);
//    }
//
//
//    context.redirect("/heladeras");
//  }

  public void formHacerseCargoHeladera(Context context) {
    Map<String, Object> model = new HashMap<>();

    agregarCiudadesProvinciasYLocalidades(model);
    List<Modelo> modelos = this.repositorioDeModelos.buscarTodos();


    List<Map<String, String>> estadosHeladera = Arrays.stream(EstadoHeladera.values())
        .map(estado -> {
          Map<String, String> estadoMap = new HashMap<>();
          estadoMap.put("valor", estado.name()); // Valor en mayúsculas
          return estadoMap;
        })
        .collect(Collectors.toList());


    model.put("estadosHeladera", estadosHeladera);
    model.put("modelos", modelos);
    model.put("titulo", "Formulario hacerse cargo heladera");

    renderWithUser(context, "colaboraciones/hacerse_cargo_heladera.hbs", model);
  }

  public void saveFormHacerseCargoHeladera(Context context) {
    Heladera nuevaHeladera = new Heladera();
    PuntoGeografico puntoGeografico = new PuntoGeografico();
    validarPuntoGeografico(context, puntoGeografico);

    String estadoString = context.formParam("estado_heladera");
    nuevaHeladera.setPuntoGeografico(puntoGeografico);
    nuevaHeladera.setCapacidadViandas(Integer.valueOf(Objects.requireNonNull(context.formParam("capacidad_viandas"))));
    nuevaHeladera.setFechaDeInicio(LocalDate.parse(Objects.requireNonNull(context.formParam("fecha_funcionamiento"))));


    String modelo_str = context.formParam("modelo");
    System.out.println("********************************* modelo: " + modelo_str + " *********************************");
    Modelo nuevoModelo = repositorioDeModelos.buscarPorID(Long.valueOf(modelo_str))
        .orElseThrow(() -> new IllegalArgumentException("Modelo no encontrado"));
    System.out.println("********************************* modelo nombre: " + nuevoModelo.getNombre() + " *********************************");
    repositorioDeModelos.guardar(nuevoModelo);

    nuevaHeladera.setModelo(nuevoModelo);

    nuevaHeladera.setEstadoHeladera2(EstadoHeladera.valueOf(estadoString));

    HacerseCargoHeladera hacerseCargoHeladera = new HacerseCargoHeladera();
    hacerseCargoHeladera.setNuevaHeladera(nuevaHeladera);

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    hacerseCargoHeladera.setColaborador(colaborador);
    hacerseCargoHeladera.agregarHeladera(nuevaHeladera);

    RegistroHeladera registroHeladera = new RegistroHeladera();
    registroHeladera.setHeladera(nuevaHeladera);
    registroHeladera.setEstado(EstadoHeladera.valueOf(estadoString));
    registroHeladera.setFecha(LocalDate.now());

    this.repositorioHeladera.guardar(nuevaHeladera);
    this.repositorioRegistroActividadHeladera.guardar(registroHeladera);
    this.repositorioContribucion.guardar(hacerseCargoHeladera);
    actualizarPuntos(colaborador, hacerseCargoHeladera);

    //context.redirect("/heladeras");
  }

  /***************************************************** REALIZAR OFERTA *****************************************************/
  public void formRealizarOferta(Context context) {


    List<Rubro> rubros = this.repositorioDeRubros.buscarTodos();
    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Realizar oferta/agregar producto");
    model.put("rubros", rubros);

    renderWithUser(context, "colaboraciones/realizar_oferta.hbs", model);
  }

  public void savePublicarProducto(Context context) {
//        if(!tieneFormaContribucion(context, FormaDeContribucion.RealizarOfertas)) {
//            context.status(HttpStatus.METHOD_NOT_ALLOWED);
//            return;
//        }
    RealizarOfertas realizarOfertas = new RealizarOfertas();
    Producto nuevoProducto = new Producto();

    String canje_nombre_str = context.formParam("canje_nombre");
    String rubro_str = context.formParam("rubro");
    String canje_puntos_str = context.formParam("canje_puntos");

    Long rubro_id = Long.valueOf(rubro_str);
    Rubro rubro = this.repositorioDeRubros.buscarPorID(rubro_id)
        .orElseThrow(() -> new IllegalArgumentException("Rubro no encontrada"));

    UploadedFile archivo = context.uploadedFile("canje_foto");

    if (archivo != null) {
      String directorioDestino = "src/main/resources/public/fotos_productos/";

      File carpeta = new File(directorioDestino);
      if (!carpeta.exists()) {
        carpeta.mkdirs();
      }

      String nombreOriginal = archivo.filename();
      String nombreArchivo = nombreOriginal.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9.-]", "_");

      String extension = "";
      int i = nombreOriginal.lastIndexOf('.');
      if (i > 0) {
        extension = nombreOriginal.substring(i);
        nombreArchivo = nombreOriginal.substring(0, i);
      }

      int contador = 1;
      File archivoDestino = new File(directorioDestino, nombreOriginal);
      while (archivoDestino.exists()) {
        nombreOriginal = nombreArchivo + contador + extension;
        archivoDestino = new File(directorioDestino, nombreOriginal);
        contador++;
      }

      // Guardar el archivo en la carpeta
      try (InputStream inputStream = archivo.content()) {
        Files.copy(inputStream, archivoDestino.toPath());

        String rutaURL = "/fotos_productos/" + URLEncoder.encode(nombreOriginal, StandardCharsets.UTF_8).replace("+", "%20");
        nuevoProducto.setImagenIlustrativa(rutaURL);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      nuevoProducto.setImagenIlustrativa(null);
    }

    nuevoProducto.setNombre(canje_nombre_str);
    nuevoProducto.setRubro(rubro);
    nuevoProducto.setCantidadDePuntos(Integer.valueOf(canje_puntos_str));

    realizarOfertas.setFechaHora(LocalDateTime.now());
    realizarOfertas.setNuevaProducto(nuevoProducto);

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    realizarOfertas.setColaborador(colaborador);
    realizarOfertas.agregarProducto(nuevoProducto);

    this.repositorioDeProductos.guardar(nuevoProducto);
    this.repositorioContribucion.guardar(realizarOfertas);
    actualizarPuntos(colaborador, realizarOfertas);
    context.redirect("/colaboraciones");
  }

  /***************************************************** REGISTRAR VULNERABLE *****************************************************/
  public void formRegistroPersonaVulnerable(Context context) {

    Map<String, Object> model = new HashMap<>();
    List<PersonaVulnerable> vulnerables = this.repositorioPersonaVulnerable.buscarTodos();
    agregarCiudadesProvinciasYLocalidades(model);

    List<Map<String, String>> tiposDocumento = Arrays.stream(TipoDocumento.values())
        .map(tipo -> {
          Map<String, String> tipoMap = new HashMap<>();
          tipoMap.put("valor", tipo.name().toLowerCase()); // Valor en minúsculas
          tipoMap.put("descripcion", tipo.getDescripcion()); // Descripción completa
          return tipoMap;
        })
        .collect(Collectors.toList());

    model.put("titulo", "Registrar vulnerable");
    model.put("tiposDocumento", tiposDocumento);
    model.put("personasVulnerables", vulnerables);

    renderWithUser(context, "colaboraciones/registrar_vulnerable.hbs", model);
  }

  public void saveRegistrarPersonaVulnerable(Context context) {
//        if(!tieneFormaContribucion(context, FormaDeContribucion.RegistroPersonaEnSituacionVulnerable)) {
//            context.status(HttpStatus.METHOD_NOT_ALLOWED);
//            return;
//        }

    PersonaVulnerable personaVulnerable = new PersonaVulnerable();

    String nombre_str = context.formParam("nombre");
    String fecha_nac_str = context.formParam("fecha_nac");
    String tipo_docu_str = context.formParam("tipo_docu");
    String numero_docu_str = context.formParam("numero_docu");
    String padreId = context.formParam("padre_id");


    validarDireccion(context, personaVulnerable);

    if (tipo_docu_str != null && !tipo_docu_str.isEmpty() &&
        numero_docu_str != null && !numero_docu_str.isEmpty()) {

      TipoDocumento tipoDocumento = TipoDocumento.valueOf(tipo_docu_str.toUpperCase());

      personaVulnerable.setTipoDocumento(tipoDocumento);
      personaVulnerable.setNumeroDocumento(numero_docu_str);
    }


    personaVulnerable.setNombre(nombre_str);
    personaVulnerable.setFechaDeNacimiento(LocalDate.parse(fecha_nac_str));

    RegistroPersonaVulnerable registroPersonaVulnerable = new RegistroPersonaVulnerable();
    registroPersonaVulnerable.setNuevoVulnerable(personaVulnerable);

    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    registroPersonaVulnerable.setColaborador(colaborador);
    registroPersonaVulnerable.agregarVulnerable(personaVulnerable);


    this.repositorioPersonaVulnerable.guardar(personaVulnerable);
    this.repositorioContribucion.guardar(registroPersonaVulnerable);

    if (padreId != null && !padreId.isEmpty()) {
      PersonaVulnerable padre = repositorioPersonaVulnerable.buscarPorID(Long.parseLong(padreId))
          .orElseThrow(() -> new IllegalArgumentException("Padre no encontrado"));
      padre.agregarMenorACargo(personaVulnerable);
      repositorioPersonaVulnerable.modificar(padre);
    }

    actualizarPuntos(colaborador, registroPersonaVulnerable);
    context.redirect("/persona_vulnerable");
  }

  private boolean tieneFormaContribucion(Context context, FormaDeContribucion formaDeContribucion) {
    Usuario usuario = getSessionUser(context);
    Colaborador colaborador = usuario.getColaborador();
    return colaborador.getTipoPersona().getFormasDeContribuir().contains(formaDeContribucion);
  }

  private Direccion getFormDireccion(Context context) {
    return new Direccion(
        this.repositorioCalle.buscarPorID(Long.valueOf(context.formParam("calle_id"))).get(),
        Integer.parseInt(context.formParam("altura")),
        context.formParam("codigoPostal"),
        this.repositorioCiudad.buscarPorID(Long.valueOf(context.formParam("ciudad_id"))).get(),
        this.repositorioLocalidad.buscarPorID(Long.valueOf(context.formParam("localidad_id"))).get()
    );
  }

  @Override
  public void delete(Context context) {
    Long contribucion_id = Long.parseLong(context.pathParam("id"));
    Contribucion contribucion = repositorioContribucion.buscarPorID(contribucion_id)
        .orElseThrow(() -> new IllegalArgumentException("Contribucion no encontrada"));

    Map<String, Object> model = new HashMap<>();
    model.put("contribucion", contribucion);
    this.repositorioContribucion.eliminar(contribucion);
    context.redirect("/colaboraciones");
  }

  /***************************************************** AUXILIARES *****************************************************/
  public void agregarCiudadesProvinciasYLocalidades(Map<String, Object> model) {
    List<Ciudad> ciudades = this.repositorioCiudad.buscarTodos();
    List<Localidad> localidades = this.repositorioLocalidad.buscarTodos();
    List<Provincia> provincias = this.repositorioProvincia.buscarTodos();

    model.put("ciudades", ciudades);
    model.put("localidades", localidades);
    model.put("provincias", provincias);
  }


  public void validarDireccion(Context context, PersonaVulnerable nuevoVulnerable) {
    Direccion direccion = new Direccion();
    Calle calle = new Calle();
    String calle_str = context.formParam("calle");
    String altura_str = context.formParam("altura");
    String ciudad_str = context.formParam("ciudad");
    String codigo_postal_str = context.formParam("codigoPostal");
    String provinciaParam = context.formParam("provincia");
    String localidadParam = context.formParam("localidad");

    if (calle_str != null && !calle_str.isEmpty() &&
        altura_str != null && !altura_str.isEmpty() &&
        codigo_postal_str != null && !codigo_postal_str.isEmpty() &&
        provinciaParam != null && !provinciaParam.isEmpty() &&
        localidadParam != null && !localidadParam.isEmpty()) {

      calle.setNombre(calle_str);
      this.repositorioCalle.guardar(calle);

      List<Localidad> localidades = repositorioLocalidad.buscarPorNombre(localidadParam, provinciaParam);
      Localidad localidad = localidades.isEmpty() ? null : localidades.get(0);

      if (localidad == null) {
        localidad = new Localidad();
        localidad.setNombre(context.formParam("localidad"));
        localidad.setProvincia(repositorioProvincia.buscarPorNombre(provinciaParam).get(0));
        repositorioLocalidad.guardar(localidad);
      }

      List<Ciudad> ciudades = repositorioCiudad.buscarPorNombre(ciudad_str);
      Ciudad ciudad = ciudades.isEmpty() ? null : ciudades.get(0);

      if (ciudad == null) {
        ciudad = new Ciudad();
        ciudad.setNombre(ciudad_str);
        repositorioCiudad.guardar(ciudad);
      }

      direccion.setCalle(calle);
      direccion.setAltura(Integer.valueOf(altura_str));
      direccion.setCodigoPostal(codigo_postal_str);
      direccion.setLocalidad(localidad);
      direccion.setCiudad(ciudad);
      this.repositorioDeDirecciones.guardar(direccion);
      nuevoVulnerable.setDireccion(direccion);
    }
  }

  public void validarPuntoGeografico(Context context, PuntoGeografico puntoGeografico) {

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

    String localidadParam = context.formParam("localidad");
    String provinciaParam = context.formParam("provincia");
    List<Localidad> localidades = repositorioLocalidad.buscarPorNombre(localidadParam, provinciaParam);
    Localidad localidad = localidades.isEmpty() ? null : localidades.get(0);

    if (localidad == null) {
      localidad = new Localidad();
      localidad.setNombre(context.formParam("localidad"));
      localidad.setProvincia(repositorioProvincia.buscarPorNombre(provinciaParam).get(0));
      repositorioLocalidad.guardar(localidad);
    }

    Direccion direccion = new Direccion();
    direccion.setCalle(calle);
    direccion.setAltura(altura);
    direccion.setCodigoPostal(codigoPostal);
    direccion.setCiudad(ciudad);
    direccion.setLocalidad(localidad);

        /* String direccion_id = context.formParam("direccion_id");
        if (direccion_id != null && !direccion_id.isEmpty()) {
            direccion.setId(Long.valueOf(direccion_id));
            this.repositorioDeDirecciones.modificar(direccion);
        } else {
            this.repositorioDeDirecciones.guardar(direccion);
        } */

    repositorioDeDirecciones.guardar(direccion);

    String nombre_pg = direccion.getCalle().getNombre() + " " + direccion.getAltura() + ", " + direccion.getCodigoPostal();
    puntoGeografico.setLatitud(Float.parseFloat(context.formParam("latitud")));
    puntoGeografico.setLongitud(Float.parseFloat(context.formParam("longitud")));
    puntoGeografico.setDireccion(direccion);
    puntoGeografico.setNombre(nombre_pg);

    repositorioPuntoGeografico.guardar(puntoGeografico);

        /* String puntroGeografico_id = context.formParam("direccion_id");
        if (puntroGeografico_id != null && !puntroGeografico_id.isEmpty()) {
            puntoGeografico.setId(Long.valueOf(puntroGeografico_id));
            this.repositorioPuntoGeografico.modificar(puntoGeografico);
        } else {
            this.repositorioPuntoGeografico.guardar(puntoGeografico);
        } */
  }


  public void actualizarPuntos(Colaborador colaborador, Contribucion contribucion) {
    if (!colaborador.getContribucionesRealizadas().contains(contribucion)) {
      colaborador.agregarContribucion(contribucion);
    }
    Integer puntos = (int) Math.ceil(calculadoraDePuntos.calcularPuntosContribuidor(colaborador));
    colaborador.setPuntosTotales(puntos);
    repositorioColaborador.modificar(colaborador);
  }
}
