package controllers;

import io.javalin.http.Context;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Ciudad;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;
import model.entities.Direccion.Provincia;
import model.entities.PersonaVulnerable.PersonaVulnerable;
import model.entities.Utils.TipoDocumento;
import model.repositories.RepositorioCalle;
import model.repositories.RepositorioCiudad;
import model.repositories.RepositorioColaborador;
import model.repositories.RepositorioDeDirecciones;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioPersonaVulnerable;
import model.repositories.RepositorioProvincia;
import model.repositories.RepositorioTarjeta;
import model.repositories.RepositorioUsuario;

public class PersonaVulnerableController extends BaseController {
  RepositorioPersonaVulnerable repositorioPersonaVulnerable;
  RepositorioDeDirecciones repositorioDeDirecciones;
  RepositorioTarjeta repositorioTarjeta;
  RepositorioColaborador repositorioColaborador;
  RepositorioCiudad repositorioCiudad;
  RepositorioLocalidad repositorioLocalidad;
  RepositorioProvincia repositorioProvincia;
  RepositorioCalle repositorioCalle;

  public PersonaVulnerableController(RepositorioPersonaVulnerable repositorioPersonaVulnerable,
                                     RepositorioDeDirecciones repositorioDeDirecciones,
                                     RepositorioTarjeta repositorioTarjeta, RepositorioColaborador repositorioColaborador,
                                     RepositorioCiudad repositorioCiudad,
                                     RepositorioLocalidad repositorioLocalidad, RepositorioProvincia repositorioProvincia,
                                     RepositorioCalle repositorioCalle,
                                     RepositorioUsuario repositorioUsuario) {
    super(repositorioUsuario);
    this.repositorioPersonaVulnerable = repositorioPersonaVulnerable;
    this.repositorioDeDirecciones = repositorioDeDirecciones;
    this.repositorioTarjeta = repositorioTarjeta;
    this.repositorioColaborador = repositorioColaborador;
    this.repositorioCiudad = repositorioCiudad;
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioProvincia = repositorioProvincia;
    this.repositorioCalle = repositorioCalle;
  }

  @Override
  public void index(Context context) {
    List<PersonaVulnerable> vulnerables = this.repositorioPersonaVulnerable.buscarTodos();

    Map<String, Object> model = new HashMap<>();

    model.put("titulo", "Persona Vulnerable");
    model.put("vulnerables", vulnerables);

    renderWithUser(context, "personaVulnerable/personaVulnerable.hbs", model);
  }

  public void mostrarFormularioEdicion(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long vulnerable_id = Long.parseLong(context.pathParam("id"));
    PersonaVulnerable personaVulnerable = repositorioPersonaVulnerable.buscarPorID(vulnerable_id)
        .orElseThrow(() -> new IllegalArgumentException("Persona vulnerable no encontrada"));

    agregarCiudadesProvinciasYLocalidades(model);

    List<TipoDocumento> tiposDocumento = Arrays.asList(TipoDocumento.values());

    model.put("personaVulnerable", personaVulnerable);
    model.put("tiposDocumento", tiposDocumento);

    renderWithUser(context, "personaVulnerable/editar_vulnerable.hbs", model);
  }

  public void detalleVulnerable(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long vulnerable_id = Long.parseLong(context.pathParam("id"));

    PersonaVulnerable vulnerable = repositorioPersonaVulnerable.buscarPorID(vulnerable_id)
        .orElseThrow(() -> new IllegalArgumentException("Persona vulnerable no encontrado"));

    String tipoDocuStr = String.valueOf(vulnerable.getTipoDocumento());

    model.put("vulnerable", vulnerable);
    model.put("tipoDocuStr", tipoDocuStr);
    renderWithUser(context, "personaVulnerable/detalle_vulnerable.hbs", model);
  }

  public PersonaVulnerable getNuevoVulnerable(Context context) {

    Direccion direccion = new Direccion();
    PersonaVulnerable personaVulnerable = new PersonaVulnerable();

    String idParam = context.formParam("id");
    if (idParam != null && !idParam.isEmpty()) {
      personaVulnerable.setId(Long.valueOf(idParam));
    }

    String nombre_str = context.formParam("nombre");
    String fecha_nac_str = context.formParam("fecha_nac");

    String tipo_docu_str = context.formParam("tipo_docu");
    String numero_docu_str = context.formParam("numero_docu");

    validarDireccionOpcional(context, direccion);
    personaVulnerable.setDireccion(direccion);

    if (tipo_docu_str != null && !tipo_docu_str.isEmpty() &&
        numero_docu_str != null && !numero_docu_str.isEmpty()) {

      TipoDocumento tipoDocumento = TipoDocumento.valueOf(tipo_docu_str.toUpperCase());

      personaVulnerable.setTipoDocumento(tipoDocumento);
      personaVulnerable.setNumeroDocumento(numero_docu_str);
    }

    personaVulnerable.setNombre(nombre_str);
    personaVulnerable.setFechaDeNacimiento(LocalDate.parse(fecha_nac_str));

    return personaVulnerable;
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) {
    PersonaVulnerable nuevoVulnerable = getNuevoVulnerable(context);

    this.repositorioPersonaVulnerable.guardar(nuevoVulnerable);
    context.redirect("/persona_vulnerable");
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) throws IOException {
    PersonaVulnerable nuevoVulnerable = getNuevoVulnerable(context);

    this.repositorioPersonaVulnerable.modificar(nuevoVulnerable);
    context.redirect("/persona_vulnerable");
  }

  @Override
  public void delete(Context context) {
    Long vulnerable_id = Long.parseLong(context.pathParam("id"));
    PersonaVulnerable vulnerable = repositorioPersonaVulnerable.buscarPorID(vulnerable_id)
        .orElseThrow(() -> new IllegalArgumentException("Persona vulnerable no encontrada"));

    Map<String, Object> model = new HashMap<>();
    model.put("vulnerable", vulnerable);
    this.repositorioPersonaVulnerable.eliminar(vulnerable);
    context.redirect("/persona_vulnerable");
  }

  /*****************************************************
   * AUXILIARES
   *****************************************************/

  public void validarDireccionOpcional(Context context, Direccion direccion) {

    String calle_str = context.formParam("calle");
    String altura_str = context.formParam("altura");
    String codigo_postal_str = context.formParam("codigoPostal");
    String provinciaParam = context.formParam("provincia");
    String localidadParam = context.formParam("localidad");

    this.repositorioDeDirecciones.guardar(direccion);

    if (calle_str != null && !calle_str.isEmpty() &&
        altura_str != null && !altura_str.isEmpty() &&
        codigo_postal_str != null && !codigo_postal_str.isEmpty() &&
        provinciaParam != null && !provinciaParam.isEmpty() &&
        localidadParam != null && !localidadParam.isEmpty()) {

      List<Calle> calles = repositorioCalle.buscarPorNombre(calle_str);
      Calle calle = calles.isEmpty() ? null : calles.get(0);

      if (calle == null) {
        calle = new Calle();
        calle.setNombre(calle_str);
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

      List<Localidad> localidades = repositorioLocalidad.buscarPorNombre(context.formParam("localidad"),
          context.formParam("provincia"));
      Localidad localidad = localidades.isEmpty() ? null : localidades.get(0);

      List<Provincia> provincias = repositorioProvincia.buscarPorNombre(context.formParam("provincia"));
      Provincia provincia = provincias.isEmpty() ? null : provincias.get(0);

      if (localidad == null) {
        localidad = new Localidad();
        localidad.setNombre(context.formParam("localidad"));
        localidad.setProvincia(provincia);
        repositorioLocalidad.guardar(localidad);
      }

      // Crear la dirección solo si todos los datos están presentes
      direccion.setCalle(calle);
      direccion.setAltura(altura);
      direccion.setCodigoPostal(codigoPostal);
      direccion.setCiudad(ciudad);
      direccion.setLocalidad(localidad);

      this.repositorioDeDirecciones.guardar(direccion);
    }
  }

  public void agregarCiudadesProvinciasYLocalidades(Map<String, Object> model) {
    List<Ciudad> ciudades = this.repositorioCiudad.buscarTodos();
    List<Localidad> localidades = this.repositorioLocalidad.buscarTodos();
    List<Provincia> provincias = this.repositorioProvincia.buscarTodos();

    model.put("ciudades", ciudades);
    model.put("localidades", localidades);
    model.put("provincias", provincias);
  }
}
