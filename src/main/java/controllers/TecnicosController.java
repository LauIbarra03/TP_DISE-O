package controllers;

import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.entities.Contrasenia.ValidadorContrasenia;
import model.entities.Contrasenia.exception.ContraseniaCortaException;
import model.entities.Contrasenia.exception.ContraseniaEntreLas10000MasComunesException;
import model.entities.Contrasenia.exception.ContraseniaIgualAUsuarioException;
import model.entities.Contrasenia.exception.ContraseniaProhibidaException;
import model.entities.Contrasenia.exception.ContraseniaSinMayusculaException;
import model.entities.Contrasenia.exception.ContraseniaSinMinusculaException;
import model.entities.Contrasenia.exception.ContraseniaSinNumeroException;
import model.entities.Direccion.Localidad;
import model.entities.Direccion.Provincia;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.MedioDeContacto.TipoContacto;
import model.entities.Tecnico.Tecnico;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.entities.Utils.TipoDocumento;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioMedioDeContacto;
import model.repositories.RepositorioProvincia;
import model.repositories.RepositorioTecnico;
import model.repositories.RepositorioUsuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TecnicosController extends BaseController {
  private final RepositorioLocalidad repositorioLocalidad;
  private final RepositorioMedioDeContacto repositorioMedioDeContacto;
  private final RepositorioTecnico repositorioTecnico;
  private final RepositorioProvincia repositorioProvincia;

  public TecnicosController(RepositorioUsuario repositorioUsuario, RepositorioLocalidad repositorioLocalidad, RepositorioMedioDeContacto repositorioMedioDeContacto,
                            RepositorioTecnico repositorioTecnico, RepositorioProvincia repositorioProvincia) {
    super(repositorioUsuario);
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioMedioDeContacto = repositorioMedioDeContacto;
    this.repositorioTecnico = repositorioTecnico;
    this.repositorioProvincia = repositorioProvincia;
  }

  public void formTecnicosRegistro(Context context) {
    Map<String, Object> model = new HashMap<>();

    List<Localidad> localidades = this.repositorioLocalidad.buscarTodos();
    List<Provincia> provincias = this.repositorioProvincia.buscarTodos();
    List<Map<String, String>> tiposDocumento = Arrays.stream(TipoDocumento.values())
        .map(tipo -> {
          Map<String, String> tipoMap = new HashMap<>();
          tipoMap.put("valor", tipo.name());
          tipoMap.put("descripcion", tipo.getDescripcion()); // Descripción completa
          return tipoMap;
        })
        .collect(Collectors.toList());

    model.put("tiposDocumento", tiposDocumento);
    model.put("localidades", localidades);
    model.put("provincias", provincias);

    model.put("titulo", "Registro Tecnico");

    context.render("registro/registro_tecnico.hbs", model);
  }

  public void saveRegistroTecnico(Context context) {
    Tecnico nuevoTecnico = new Tecnico();

    validarMedioDeContactoYAgregar(context, nuevoTecnico);
    nuevoTecnico.setNombre(context.formParam("nombre"));
    nuevoTecnico.setApellido(context.formParam("apellido"));
    String cuil_str = context.formParam("cuil");
    String tipo_docu_str = context.formParam("tipo_docu");
    String numero_docu_str = context.formParam("numero_docu");
    String nombreProvincia = context.formParam("provincia");
    List<String> nombresLocalidades = context.formParams("areasCobertura");

    if (tipo_docu_str != null && !tipo_docu_str.isEmpty() &&
        numero_docu_str != null && !numero_docu_str.isEmpty() &&
        cuil_str != null && !cuil_str.isEmpty()) {

      TipoDocumento tipoDocumento = TipoDocumento.valueOf(tipo_docu_str.toUpperCase());

      nuevoTecnico.setTipoDocumento(tipoDocumento);
      nuevoTecnico.setNroDocumento(numero_docu_str);
      nuevoTecnico.setCuil(cuil_str);
    }

    Provincia provincia = repositorioProvincia.buscarPorNombreUnico(nombreProvincia);
    List<Localidad> localidades_modificadas = new ArrayList<>();
    for (String nombreLocalidad : nombresLocalidades) {
      // Buscar la localidad por nombre y provincia
      Localidad localidadEncontrada = repositorioLocalidad.buscarPorNombreProvinciaYLocalidad(nombreLocalidad, nombreProvincia);

      Localidad localidad;

      if (localidadEncontrada == null) {
        // Crear y guardar una nueva localidad si no existe
        localidad = new Localidad();
        localidad.setNombre(nombreLocalidad);
        localidad.setProvincia(provincia);

        repositorioLocalidad.guardar(localidad);
      } else {
        // Usar la localidad existente
        localidad = localidadEncontrada;
      }

      // Agregar la localidad a la lista de localidades modificadas
      localidades_modificadas.add(localidad);
    }

    validarClaveYGuardar(context, nuevoTecnico, localidades_modificadas);
  }

  public void perfilUsuario(Context context) {
    Map<String, Object> model = new HashMap<>();

    Long tecnico_id = Long.parseLong(context.pathParam("id"));
    Tecnico tecnico = this.repositorioTecnico.buscarPorID(tecnico_id)
        .orElseThrow(() -> new IllegalArgumentException("Tecnico no encontrado"));

    model.put("tecnico", tecnico);
    renderWithUser(context, "tecnicos/perfil_tecnico.hbs", model);
  }

  public void validarMedioDeContactoYAgregar(Context context, Tecnico nuevoTecnico) {
    String email = context.formParam("email");
    String whatsapp = context.formParam("whatsapp");
    String telefono = context.formParam("telefono");
    String telegram = context.formParam("telegram");

    String medioDeContactoElegido = context.formParam("medioContacto");
    MedioDeContacto medioDeContacto = null;
    if ("email".equals(medioDeContactoElegido)) {
      medioDeContacto = new MedioDeContacto(TipoContacto.CORREO, email);
      this.repositorioMedioDeContacto.guardar(medioDeContacto);
    } else if ("telefono".equals(medioDeContactoElegido)) {
      medioDeContacto = new MedioDeContacto(TipoContacto.TELEFONO, telefono);
      this.repositorioMedioDeContacto.guardar(medioDeContacto);
    } else if ("whatsapp".equals(medioDeContactoElegido)) {
      medioDeContacto = new MedioDeContacto(TipoContacto.WHATSAPP, whatsapp);
      this.repositorioMedioDeContacto.guardar(medioDeContacto);
    } else if ("telegram".equals(medioDeContactoElegido)) {
      medioDeContacto = new MedioDeContacto(TipoContacto.TELEGRAM, telegram);
      this.repositorioMedioDeContacto.guardar(medioDeContacto);
    }

    nuevoTecnico.setMedioDeContacto(medioDeContacto);
  }

  public void validarClaveYGuardar(Context context, Tecnico nuevoTecnico, List<Localidad> localidadesModificadas) {
    String usuario = context.formParam("usuario");
    String password = context.formParam("password");
    Usuario nuevoUsuario = new Usuario(usuario, password, nuevoTecnico, TipoRol.TECNICO);
    // Dejo el viejo por las dudas si esto genera un error
    // String renderPath = "/registro/registro_tecnico.hbs";
    String renderPath = "registro/registro_tecnico.hbs";
    try {
      ValidadorContrasenia validador = new ValidadorContrasenia();
      validador.validarClave(nuevoUsuario, password);

      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String passwordHasheada = passwordEncoder.encode(password);
      nuevoUsuario.setPassword(passwordHasheada);

      this.repositorioUsuario.guardar(nuevoUsuario);
      this.repositorioTecnico.guardar(nuevoTecnico);
      for (Localidad localidad : localidadesModificadas) {
        localidad.agregarTecnico(nuevoTecnico);
        this.repositorioLocalidad.modificar(localidad);
      }


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
}
