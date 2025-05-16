package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import model.entities.CargaMasiva.CargaMasiva;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.FormaDeContribucion;
import model.entities.Colaborador.TipoOrganizacion;
import model.entities.Colaborador.TipoPersona;
import model.entities.Contrasenia.ValidadorContrasenia;
import model.entities.Contrasenia.exception.ContraseniaCortaException;
import model.entities.Contrasenia.exception.ContraseniaEntreLas10000MasComunesException;
import model.entities.Contrasenia.exception.ContraseniaIgualAUsuarioException;
import model.entities.Contrasenia.exception.ContraseniaProhibidaException;
import model.entities.Contrasenia.exception.ContraseniaSinMayusculaException;
import model.entities.Contrasenia.exception.ContraseniaSinMinusculaException;
import model.entities.Contrasenia.exception.ContraseniaSinNumeroException;
import model.entities.Contribucion.Contribucion;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Ciudad;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;
import model.entities.Direccion.Provincia;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.MedioDeContacto.TipoContacto;
import model.entities.Notificador.Notificador;
import model.entities.Producto.Rubro;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.entities.Utils.TipoDocumento;
import model.repositories.RepositorioCalle;
import model.repositories.RepositorioCiudad;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioContribucion;
import model.repositories.RepositorioDeDirecciones;
import model.repositories.RepositorioDeRubros;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioMedioDeContacto;
import model.repositories.RepositorioProvincia;
import model.repositories.RepositorioTipoPersona;
import model.repositories.RepositorioUsuario;
import org.apache.commons.text.RandomStringGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ColaboradoresController extends BaseController {
  private final RepositorioColaborador repositorioColaborador;
  private final RepositorioProvincia repositorioProvincia;
  private final RepositorioCiudad repositorioCiudad;
  private final RepositorioLocalidad repositorioLocalidad;
  private final RepositorioTipoPersona repositorioTipoPersona;
  private final RepositorioDeDirecciones repositorioDeDirecciones;
  private final RepositorioCalle repositorioCalle;
  private final RepositorioMedioDeContacto repositorioMedioDeContacto;
  private final RepositorioDeRubros repositorioDeRubros;
  private final RepositorioContribucion repositorioContribucion;

  public ColaboradoresController(RepositorioColaborador repositorioColaborador, RepositorioUsuario repositorioUsuario, RepositorioProvincia repositorioProvincia,
                                 RepositorioCiudad repositorioCiudad, RepositorioLocalidad repositorioLocalidad, RepositorioTipoPersona repositorioTipoPersona,
                                 RepositorioDeDirecciones repositorioDeDirecciones, RepositorioCalle repositorioCalle,
                                 RepositorioMedioDeContacto repositorioMedioDeContacto, RepositorioDeRubros repositorioDeRubros, RepositorioContribucion repositorioContribucion) {
    super(repositorioUsuario);
    this.repositorioColaborador = repositorioColaborador;
    this.repositorioProvincia = repositorioProvincia;
    this.repositorioCiudad = repositorioCiudad;
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioTipoPersona = repositorioTipoPersona;
    this.repositorioDeDirecciones = repositorioDeDirecciones;
    this.repositorioCalle = repositorioCalle;
    this.repositorioMedioDeContacto = repositorioMedioDeContacto;
    this.repositorioDeRubros = repositorioDeRubros;
    this.repositorioContribucion = repositorioContribucion;
  }

  /***************************************************** INDEX *****************************************************/
  @Override
  public void index(Context context) {

    List<Colaborador> colaboradores = this.repositorioColaborador.buscarTodos();

    Map<String, Object> model = new HashMap<>();
    model.put("colaboradores", colaboradores);
    model.put("titulo", "Colaboradores");

    renderWithUser(context, "colaboradores/colaboradores.hbs", model);
  }

  /***************************************************** PERFIL USUARIO *****************************************************/
  public void perfilUsuario(Context context) {
    Map<String, Object> model = new HashMap<>();
    Long colaboradorId = Long.parseLong(context.pathParam("id"));

    Colaborador colaborador = conseguirColaborador(colaboradorId);

    TipoRol rolColaborador = repositorioUsuario.buscarTipoRolPorColaboradorId(colaboradorId)
        .orElseThrow(() -> new IllegalArgumentException("No se encontró el rol para el colaborador con ID: " + colaboradorId));

    validarContacto(model, colaborador);
    Set<FormaDeContribucion> formasContribuir = colaborador.getFormasDeContribuirSeleccionadas();
    List<Contribucion> contribuciones = repositorioContribucion.buscarPorColaboradorId(colaboradorId);

    Long totalDistribucionesViandas = repositorioContribucion.contarDistribucionViandasPorColaboradorId(colaboradorId);
    Long totalDonacionesDinero = repositorioContribucion.sumarDonacionDineroPorColaboradorId(colaboradorId);
    if (totalDonacionesDinero == null) {
      totalDonacionesDinero = 0L;
    }

    Long totalPersonasVulnerables = repositorioContribucion.contarPersonasVulnerablesRegistradasPorColaboradorId(colaboradorId);
    Long totalOfertasRealizadas = repositorioContribucion.contarRealizarOfertasPorColaboradorId(colaboradorId);
    Long totalCargoHeladera = repositorioContribucion.contarHacerseCargoHeladeraPorColaboradorId(colaboradorId);
    Long totalDonacionesVianda = repositorioContribucion.contarDonacionViandaPorColaboradorId(colaboradorId);

    model.put("rolColaborador", rolColaborador);
    model.put("colaborador", colaborador);
    model.put("totalDistribucionesViandas", totalDistribucionesViandas);
    model.put("totalDonacionesDinero", totalDonacionesDinero);
    model.put("totalPersonasVulnerables", totalPersonasVulnerables);
    model.put("totalOfertasRealizadas", totalOfertasRealizadas);
    model.put("totalCargoHeladera", totalCargoHeladera);
    model.put("totalDonacionesVianda", totalDonacionesVianda);
    model.put("contribuciones", contribuciones);
    model.put("formasContribuir", formasContribuir);
    renderWithUser(context, "colaboradores/perfil_colaborador.hbs", model);
  }

  @Override
  public void show(@NotNull Context context) {
    Optional<Colaborador> posibleColaboradorBuscado = this.repositorioColaborador.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleColaboradorBuscado.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("colaborador", posibleColaboradorBuscado.get());

    context.render("colaboradores/detalle_colaboracion.hbs", model);
  }

  @Override
  public void create(Context context) {
    context.render("colaboradores/formulario_colaborador.hbs");
  }

  public void createRegistro(Context context) {
    context.render("colaboradores/formulario_colaborador_registro.hbs");
  }

  /***************************************************** PERSONA HUMANA *****************************************************/

  @SuppressWarnings("checkstyle:LocalVariableName")
  private Colaborador getNuevoColaboradorHumano(Context context) {
    Colaborador nuevoColaborador = new Colaborador();

    String idParam = context.formParam("id");
    if (idParam != null && !idParam.isEmpty()) {
      nuevoColaborador = this.repositorioColaborador.buscarPorID(Long.valueOf(idParam))
          .orElseThrow(() -> new IllegalArgumentException("Colaborador no encontrado"));
    }

    Direccion direccion = validarDireccionOpcional(context, nuevoColaborador);
    validarMedioDeContactoYAgregar(context, nuevoColaborador);

    TipoPersona tipoPersona = this.repositorioTipoPersona.obtenerTipoPersona("Humana");

    List<String> contribucionesSeleccionadas = context.formParams("contribuciones");

    Set<FormaDeContribucion> formasDeContribuirSeleccionadas = contribucionesSeleccionadas.stream()
        .map(FormaDeContribucion::valueOf)
        .collect(Collectors.toSet());

    String tipo_docu_str = context.formParam("tipo_docu");
    String numero_docu_str = context.formParam("numero_docu");

    if (tipo_docu_str != null && !tipo_docu_str.isEmpty() &&
        numero_docu_str != null && !numero_docu_str.isEmpty()) {
      System.out.println("ENTRÓ AL IF con nro docu: " + numero_docu_str + " y tipo docu: " + tipo_docu_str);

      TipoDocumento tipoDocumento = TipoDocumento.valueOf(tipo_docu_str.toUpperCase());

      nuevoColaborador.setTipoDoc(tipoDocumento);
      nuevoColaborador.setDocumento(numero_docu_str);
    }


    nuevoColaborador.setFormasDeContribuirSeleccionadas(formasDeContribuirSeleccionadas);
    nuevoColaborador.setNombre(context.formParam("nombre"));
    nuevoColaborador.setApellido(context.formParam("apellido"));
    nuevoColaborador.setFechaNacimiento(LocalDate.parse(Objects.requireNonNull(context.formParam("fechaNac"))));
    nuevoColaborador.setTipoPersona(tipoPersona);
    nuevoColaborador.setDireccion(direccion);
    return nuevoColaborador;
  }

  @Override
  public void save(Context context) {
    Colaborador nuevoColaborador = getNuevoColaboradorHumano(context);

    this.repositorioColaborador.guardar(nuevoColaborador);
    context.redirect("/colaboradores");
  }


  /***************************************************** PERSONA JURIDICA *****************************************************/

  private Colaborador getNuevoColaboradorJuridico(Context context) {
    Colaborador nuevoColaborador = new Colaborador();

    String idParam = context.formParam("id");
    if (idParam != null && !idParam.isEmpty()) {
      nuevoColaborador = this.repositorioColaborador.buscarPorID(Long.valueOf(idParam))
          .orElseThrow(() -> new IllegalArgumentException("Colaborador no encontrado"));
    }

    Direccion direccion = validarDireccionOpcional(context, nuevoColaborador);
    validarMedioDeContactoYAgregar(context, nuevoColaborador);

    TipoPersona tipoPersona = this.repositorioTipoPersona.obtenerTipoPersona("Jurídica");

    String razon_str = context.formParam("razon");
    String tipo_id_str = context.formParam("tipo_id");
    String rubro_id_str = context.formParam("rubro_id");

    Long rubro_id = Long.valueOf(rubro_id_str);

    Rubro rubro = this.repositorioDeRubros.buscarPorID(rubro_id)
        .orElseThrow(() -> new IllegalArgumentException("Rubro no encontrado"));

    List<String> contribucionesSeleccionadas = context.formParams("contribuciones");

    Set<FormaDeContribucion> formasDeContribuirSeleccionadas = contribucionesSeleccionadas.stream()
        .map(FormaDeContribucion::valueOf)
        .collect(Collectors.toSet());

    nuevoColaborador.setRazonSocial(razon_str);
    nuevoColaborador.setTipoOrganizacion(TipoOrganizacion.valueOf(tipo_id_str));
    nuevoColaborador.setRubro(rubro);
    nuevoColaborador.setTipoPersona(tipoPersona);
    nuevoColaborador.setFormasDeContribuirSeleccionadas(formasDeContribuirSeleccionadas);


    nuevoColaborador.setDireccion(direccion);

    return nuevoColaborador;
  }

  /***************************************************** DETALLE COLABORADOR *****************************************************/
  public void mostrarDetalleColaborador(Context ctx) {
    Map<String, Object> modelo = new HashMap<>();

    Long colaboradorId = Long.parseLong(ctx.pathParam("id"));

    Colaborador colaborador = conseguirColaborador(colaboradorId);

    Set<FormaDeContribucion> formasContribuir = colaborador.getFormasDeContribuirSeleccionadas();

    List<Contribucion> contribuciones = repositorioContribucion.buscarPorColaboradorId(colaboradorId);

    validarContacto(modelo, colaborador);

    modelo.put("colaborador", colaborador);
    modelo.put("contribuciones", contribuciones);
    modelo.put("formasContribuir", formasContribuir);

    renderWithUser(ctx, "colaboradores/detalle_colaborador.hbs", modelo);
  }

  /***************************************************** OTROS *****************************************************/
  public void saveRegistro(Context context) {
    Colaborador nuevoColaborador = getNuevoColaboradorHumano(context);
//        Usuario nuevoUsuario = new Usuario();
//        nuevoUsuario.setUsername(context.formParam("username"));
//        nuevoUsuario.setPassword(context.formParam("password"));
//        if(nuevoColaborador.getTipoPersona().getNombre() == "HUMANA") {
//            nuevoUsuario.setTipoRol(TipoRol.PERSONA_HUMANA);
//        } else {
//            nuevoUsuario.setTipoRol(TipoRol.PERSONA_HUMANA);
//        }
//        nuevoUsuario.setColaborador(nuevoColaborador);
//
//        context.sessionAttribute("user", nuevoUsuario);

//        this.repositorioUsuario.guardar(nuevoUsuario);
    context.redirect("/panel");
  }

  @Override
  public void edit(Context context) {
    Optional<Colaborador> posibleConlaboradorBuscado = this.repositorioColaborador.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleConlaboradorBuscado.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("colaborador", posibleConlaboradorBuscado.get());
    model.put("edicion", true);

    context.render("colaborador/detalle_colaborador.hbs", model);
  }

  @Override
  public void update(Context context) {
    Colaborador nuevoColaborador = getNuevoColaboradorHumano(context);

    this.repositorioColaborador.modificar(nuevoColaborador);
    context.redirect("/colaboradores");
  }

  @Override
  public void delete(Context context) {
    Optional<Colaborador> posibleColaboradorBuscado = this.repositorioColaborador.buscarPorID(Long.valueOf(context.pathParam("id")));

    if (posibleColaboradorBuscado.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("colaborador", posibleColaboradorBuscado.get());
    this.repositorioColaborador.eliminar(posibleColaboradorBuscado.get());
    context.redirect("/colaboradores");
  }

  /***************************************************** REGISTRO *****************************************************/

  public void formRegistroHumano(Context context) {
    Map<String, Object> model = new HashMap<>();

    agregarCiudadesProvinciasYLocalidades(model);
    List<Map<String, String>> tiposDocumento = Arrays.stream(TipoDocumento.values())
        .map(tipo -> {
          Map<String, String> tipoMap = new HashMap<>();
          tipoMap.put("valor", tipo.name().toLowerCase()); // Valor en minúsculas
          tipoMap.put("descripcion", tipo.getDescripcion()); // Descripción completa
          return tipoMap;
        })
        .collect(Collectors.toList());

    model.put("tiposDocumento", tiposDocumento);
    model.put("titulo", "Registro Persona Humana");

    context.render("registro/registro_humana.hbs", model);
  }

  public void formRegistroJuridico(Context context) {
    Map<String, Object> model = new HashMap<>();

    List<Rubro> rubros = this.repositorioDeRubros.buscarTodos();
    List<Map<String, String>> tiposOrganizacion = Arrays.stream(TipoOrganizacion.values())
        .map(tipo -> {
          Map<String, String> tipoMap = new HashMap<>();
          tipoMap.put("valor", tipo.name());
          return tipoMap;
        })
        .collect(Collectors.toList());

    agregarCiudadesProvinciasYLocalidades(model);

    model.put("rubros", rubros);
    model.put("tiposOrganizacion", tiposOrganizacion);
    model.put("titulo", "Registro Persona Juridica");

    context.render("registro/registro_juridica.hbs", model);
  }

  public void saveRegistroHumano(Context context) {
    Colaborador nuevoColaborador = getNuevoColaboradorHumano(context);

    validarClaveYGuardar(context, nuevoColaborador, TipoRol.PERSONA_HUMANA);
  }

  public void saveRegistroJuridico(Context context) {
    Colaborador nuevoColaborador = getNuevoColaboradorJuridico(context);

    validarClaveYGuardar(context, nuevoColaborador, TipoRol.PERSONA_JURIDICA);
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

  public void validarContacto(Map<String, Object> modelo, Colaborador colaborador) {
    MedioDeContacto telefono = null;
    MedioDeContacto correo = null;
    MedioDeContacto whatsapp = null;
    MedioDeContacto telegram = null;

    for (MedioDeContacto contacto : colaborador.getContactos()) {
      switch (contacto.getTipoContacto()) {
        case TELEFONO:
          telefono = new MedioDeContacto(TipoContacto.TELEFONO, contacto.getContacto());
          break;
        case CORREO:
          correo = new MedioDeContacto(TipoContacto.CORREO, contacto.getContacto());
          break;
        case WHATSAPP:
          whatsapp = new MedioDeContacto(TipoContacto.WHATSAPP, contacto.getContacto());
          break;
        case TELEGRAM:
          telegram = new MedioDeContacto(TipoContacto.TELEGRAM, contacto.getContacto());
          break;
      }
    }

    modelo.put("telefono", telefono);
    modelo.put("correo", correo);
    modelo.put("whatsapp", whatsapp);
    modelo.put("telegram", telegram);
  }

  public void validarClaveYGuardar(Context context, Colaborador nuevoColaborador, TipoRol tipoRol) {
    String usuario = context.formParam("usuario");
    String password = context.formParam("password");
    Usuario nuevoUsuario = new Usuario(usuario, password, nuevoColaborador, tipoRol);

    String renderPath = tipoRol.equals(TipoRol.PERSONA_HUMANA) ? "registro/registro_humana.hbs" : "registro/registro_juridica.hbs";
    try {
      ValidadorContrasenia validador = new ValidadorContrasenia();
      validador.validarClave(nuevoUsuario, password);

      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String passwordHasheada = passwordEncoder.encode(password);
      nuevoUsuario.setPassword(passwordHasheada);

      this.repositorioUsuario.guardar(nuevoUsuario);
      this.repositorioColaborador.guardar(nuevoColaborador);

      context.sessionAttribute("user_id", nuevoUsuario.getId());
      //context.redirect("/panel");

    } catch (ContraseniaCortaException e) {
      String error = "La contraseña es demasiado corta.";
      context.status(400).json(Map.of("error", error));
    } catch (ContraseniaSinMayusculaException e) {
      String error = "La contraseña debe poseer al menos una letra mayúscula.";
      context.status(400).json(Map.of("error", error));
    } catch (ContraseniaSinMinusculaException e) {
      String error = "La contraseña debe poseer al menos una letra minúscula.";
      context.status(400).json(Map.of("error", error));
    } catch (ContraseniaSinNumeroException e) {
      String error = "La contraseña debe poseer al menos un numero.";
      context.status(400).json(Map.of("error", error));
    } catch (ContraseniaIgualAUsuarioException e) {
      String error = "La contraseña no puede ser igual al nombre de usuario.";
      context.status(400).json(Map.of("error", error));
    } catch (ContraseniaProhibidaException e) {
      String error = "La contraseña elegida no está permitida.";
      context.status(400).json(Map.of("error", error));
    } catch (ContraseniaEntreLas10000MasComunesException e) {
      String error = "La contraseña es demasiado común.";
      context.status(400).json(Map.of("error", error));
    }
  }

  public Colaborador conseguirColaborador(Long colaboradorId) {

    return repositorioColaborador.buscarPorID(colaboradorId)
        .orElseThrow(() -> new IllegalArgumentException("Colaborador no encontrado"));
  }

  public Direccion validarDireccionOpcional(Context context, Colaborador colaborador) {
    String ingresarDireccion = context.formParam("ingresarDireccion");

    // Si el usuario seleccionó "sí", crea o actualiza la dirección
    if ("si".equals(ingresarDireccion)) {

      List<Calle> calles = this.repositorioCalle.buscarPorNombre(context.formParam("calle"));
      Calle calle = calles.isEmpty() ? null : calles.get(0);

      if (calle == null) {
        calle = new Calle();
        calle.setNombre(context.formParam("calle"));
        this.repositorioCalle.guardar(calle);
      }

      Integer altura = Integer.valueOf(context.formParam("altura"));
      String codigoPostal = context.formParam("codigoPostal");

      List<Ciudad> ciudades = this.repositorioCiudad.buscarPorNombre(context.formParam("ciudad"));
      Ciudad ciudad = ciudades.isEmpty() ? null : ciudades.get(0);

      if (ciudad == null) {
        ciudad = new Ciudad();
        ciudad.setNombre(context.formParam("ciudad"));
        this.repositorioCiudad.guardar(ciudad);
      }

      List<Localidad> localidades = this.repositorioLocalidad.buscarPorNombre(context.formParam("localidad"), context.formParam("provincia"));
      Localidad localidad = localidades.isEmpty() ? null : localidades.get(0);

      if (localidad == null) {
        localidad = new Localidad();
        localidad.setNombre(context.formParam("localidad"));
        this.repositorioLocalidad.guardar(localidad);
      }

      Direccion direccion = new Direccion();
      direccion.setCalle(calle);
      direccion.setAltura(altura);
      direccion.setCodigoPostal(codigoPostal);
      direccion.setCiudad(ciudad);
      direccion.setLocalidad(localidad);

      repositorioDeDirecciones.guardar(direccion);

      return direccion;
    }

    if ("no".equals(ingresarDireccion)) {
      if (colaborador.getDireccion() != null) {
        return colaborador.getDireccion();
      } else {
        return null;
      }
    }

    return null;
  }


  public void validarMedioDeContactoYAgregar(Context context, Colaborador nuevoColaborador) {

    String email = context.formParam("email");
    String whatsapp = context.formParam("whatsapp");
    String telefono = context.formParam("telefono");
    String telegram = context.formParam("telegram");

    List<MedioDeContacto> contactos = new ArrayList<>();
    if (email != null && !email.isEmpty()) {
      MedioDeContacto medioDeContacto = new MedioDeContacto(TipoContacto.CORREO, email);
      contactos.add(medioDeContacto);
      this.repositorioMedioDeContacto.guardar(medioDeContacto);
    }
    if (whatsapp != null && !whatsapp.isEmpty()) {
      MedioDeContacto medioDeContacto2 = new MedioDeContacto(TipoContacto.WHATSAPP, whatsapp);
      contactos.add(medioDeContacto2);
      this.repositorioMedioDeContacto.guardar(medioDeContacto2);
    }
    if (telefono != null && !telefono.isEmpty()) {
      MedioDeContacto medioDeContacto3 = new MedioDeContacto(TipoContacto.TELEFONO, telefono);
      contactos.add(medioDeContacto3);
      this.repositorioMedioDeContacto.guardar(medioDeContacto3);
    }
    if (telegram != null && !telegram.isEmpty()) {
      MedioDeContacto medioDeContacto4 = new MedioDeContacto(TipoContacto.TELEGRAM, telegram);
      contactos.add(medioDeContacto4);
      this.repositorioMedioDeContacto.guardar(medioDeContacto4);
    }
    nuevoColaborador.setContactos(contactos);
  }

  //-----------------------------CARGA CSV--------------------------------

  public void procesarCSV(Context context) {
    UploadedFile archivoSubido = context.uploadedFile("archivo");

    if (archivoSubido == null) {
      context.result("No se ha subido ningún archivo.");
      return;
    }

    try {
      String archivoCSV = guardarArchivo(archivoSubido);
      System.out.println("Archivo CSV guardado en: " + archivoCSV);


      CargaMasiva cargaMasiva = new CargaMasiva();
      List<Colaborador> colaboradores = cargaMasiva.cargarDatosCSV(archivoCSV);

      System.out.println("Total de colaboradores procesados: " + colaboradores.size());

      for (Colaborador colaborador : colaboradores) {
        System.out.println("Procesando colaborador: " + colaborador.getNombre() + " con documento: " + colaborador.getDocumento());

        Optional<Colaborador> colaboradorExistenteOpt = repositorioColaborador.buscarPorNumeroDocumento(colaborador.getDocumento(),colaborador.getTipoDoc());

        if (colaboradorExistenteOpt.isPresent()) {
          Colaborador colaboradorExistente = colaboradorExistenteOpt.get();
          System.out.println("El colaborador ya existe: " + colaboradorExistente.getNombre());
          // Combinar contribuciones
          for (Contribucion contribucion : colaborador.getContribucionesRealizadas()) {
            contribucion.setColaborador(colaboradorExistente);
            colaboradorExistente.agregarContribucion(contribucion);
          }
          repositorioColaborador.modificar(colaboradorExistente);
        }
        else {
          try {
            setearNuevoColaborador(colaborador);
            System.out.println("Colaborador guardado exitosamente: " + colaborador.getNombre());
          } catch (Exception ex) {
            System.err.println("Error al guardar el colaborador: " + colaborador.getDocumento());
            ex.printStackTrace();
            continue;
          }

          Optional<Usuario> usuarioExistenteOpt = repositorioUsuario.findByColaborador(colaborador);

          if (usuarioExistenteOpt.isEmpty()) {
            System.out.println("No se encontró un usuario asociado al colaborador con documento: " + colaborador.getDocumento());
            String mail = colaborador.getContactoPorMail();
            String password = generarContraseniaAleatoria();
            System.out.println("Archivo: contraseña :  " + password);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passwordHasheada = passwordEncoder.encode(password);
            System.out.println("Archivo: contraseña :  " + passwordHasheada);
            TipoRol tipoRol = TipoRol.PERSONA_HUMANA;

            Usuario nuevoUsuario = new Usuario(mail, passwordHasheada, colaborador, tipoRol);
            try {
              repositorioUsuario.guardar(nuevoUsuario);
              System.out.println("Usuario creado exitosamente para el colaborador con documento: " + colaborador.getDocumento());

            Notificador notificador = Notificador.getInstance();
            notificador.asunto("Credenciales de Acceso al Sistema");
            String mensaje = String.format(
                "Estimado/a %s %s,\n\nGracias por su aporte. Se han generado sus credenciales de acceso:\n" +
                    "Usuario: %s\nContraseña: %s\n\nPor favor, inicie sesión para más detalles.",
                colaborador.getNombre(), colaborador.getApellido(), mail, password
            );
            System.out.println("Email: " + mail);
            notificador.notificar(colaborador.obtenerPrimerContacto(),mensaje);

            } catch (Exception ex) {
              System.err.println("Error al guardar el usuario para el colaborador: " + colaborador.getDocumento());
              ex.printStackTrace();
            }
          } else {
            System.out.println("El usuario ya existe para el colaborador con documento: " + colaborador.getDocumento());
          }
        }
      }
      context.redirect("colaboradores");
    } catch (Exception e) {
      e.printStackTrace();
      context.result("Error al procesar el archivo: " + e.getMessage());
    }
  }

  public static final int LONGITUD_CONTRASENIA = 12;

  private static final RandomStringGenerator generador = new RandomStringGenerator.Builder()
      .withinRange('0', 'z')
      .filteredBy(
          c -> Character.isLetterOrDigit(c) || "!@#$%^&*()-_=+[{]}|;:'\",<.>/?".indexOf(c) >= 0
      )
      .build();

  public static String generarContraseniaAleatoria() {
    return generador.generate(LONGITUD_CONTRASENIA);
  }

  public String guardarArchivo(UploadedFile archivoSubido) {
    try {
      String rutaArchivo = "uploads/csv/Colaboracion.csv";

      File directorio = new File("uploads/csv");
      if (!directorio.exists() && !directorio.mkdirs()) {
        throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
      }

      File archivo = new File(rutaArchivo);
      try (InputStream inputStream = archivoSubido.content()) {
        Files.copy(inputStream, archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Archivo guardado correctamente en: " + rutaArchivo);
        return rutaArchivo;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());
    }
  }

  public void setearNuevoColaborador( Colaborador colaborador){
    colaborador.setFormasDeContribuirSeleccionadas(new HashSet<>(Arrays.asList(
        FormaDeContribucion.DonacionDinero,
        FormaDeContribucion.DonacionVianda,
        FormaDeContribucion.DistribucionDeViandas,
        FormaDeContribucion.RegistroPersonaEnSituacionVulnerable
    )));
    TipoPersona personaHumana = repositorioTipoPersona.obtenerTipoPersona("Humana");
    colaborador.setTipoPersona(personaHumana);
    for (Contribucion contribucion : colaborador.getContribucionesRealizadas()){
      contribucion.setColaborador(colaborador);
    }
    repositorioColaborador.guardar(colaborador);
  }

}
