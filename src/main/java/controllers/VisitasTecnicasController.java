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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Contribucion.Contribucion;
import model.entities.Direccion.Localidad;
import model.entities.Heladera.Heladera;
import model.entities.Incidente.Incidente;
import model.entities.Incidente.TipoIncidente;
import model.entities.Tecnico.RegistroVisitaTecnica;
import model.entities.Tecnico.Tecnico;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioLocalidad;
import model.repositories.RepositorioRegistroVisitaTecnica;
import model.repositories.RepositorioUsuario;

public class VisitasTecnicasController extends BaseController {
  private final RepositorioLocalidad repositorioLocalidad;
  private final RepositorioRegistroVisitaTecnica repositorioRegistroVisitaTecnica;
  private final RepositorioDeIncidentes repositorioDeIncidentes;

  public VisitasTecnicasController(RepositorioUsuario repositorioUsuario, RepositorioLocalidad repositorioLocalidad, RepositorioRegistroVisitaTecnica repositorioRegistroVisitaTecnica, RepositorioDeIncidentes repositorioDeIncidentes) {
    super(repositorioUsuario);
    this.repositorioLocalidad = repositorioLocalidad;
    this.repositorioRegistroVisitaTecnica = repositorioRegistroVisitaTecnica;
    this.repositorioDeIncidentes = repositorioDeIncidentes;
  }

  @Override
  public void index(Context context) {

    Usuario usuario = getSessionUser(context);
    if (usuario != null) {
      List<RegistroVisitaTecnica> visitasHeladerasPropias = switch (usuario.getTipoRol()) {
        case ADMIN -> this.repositorioRegistroVisitaTecnica.buscarTodos();
        case TECNICO -> obtenerVisitasRealizadasPorTecnico(context);
        case PERSONA_JURIDICA -> obtenerVisitasDeHeladerasPropias(context);
        default -> List.of();
      };

      Map<String, Object> model = new HashMap<>();
      model.put("visitas", visitasHeladerasPropias);
      model.put("titulo", "Listado de Visitas Tecnicas");

      renderWithUser(context, "visitasTecnicas/visitasTecnicas.hbs", model);
    }
  }

  @Override
  public void show(Context context) {
    RegistroVisitaTecnica visita = this.repositorioRegistroVisitaTecnica.buscarPorID(Long.valueOf(context.pathParam("id")))
        .orElseThrow(() -> new IllegalArgumentException("Visita tecnica no encontrada"));
    String tipoAlerta = null;
    Incidente incidente = visita.getIncidente();
    if (incidente.getTipoIncidente() == TipoIncidente.Alerta) {
      switch (incidente.getTipoAlerta()) {
        case Fraude:
          tipoAlerta = "Fraude";
          break;
        case FallaConexion:
          tipoAlerta = "Falla en la conexión";
          break;
        case Temperatura:
          tipoAlerta = "Temperatura fuera del rango permitido";
          break;
      }
    }
    Map<String, Object> model = new HashMap<>();

    if (visita.getFoto() != null) {
      String rutaCompleta = visita.getFoto();

      if (rutaCompleta.startsWith("src/main/resources")) {
        String rutaRelativa = rutaCompleta.substring("src/main/resources".length());
        System.out.println("**************** " + rutaRelativa + " ****************");
        model.put("foto", rutaRelativa);
      }
    }

    model.put("visita", visita);
    model.put("tipoAlerta", tipoAlerta);

    renderWithUser(context, "visitasTecnicas/detalle_visitaTecnica.hbs", model);
  }

  @Override
  public void save(Context context) {
    RegistroVisitaTecnica nuevaVisita = new RegistroVisitaTecnica();

    nuevaVisita.setDescripcion(context.formParam("descripcion"));
    UploadedFile archivo = context.uploadedFile("foto");

    if (archivo != null && archivo.size() > 0) {
      String directorioDestino = "src/main/resources/public/fotos_visita_tecnica/";

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

        String rutaURL = "/fotos_visita_tecnica/" + URLEncoder.encode(nombreOriginal, StandardCharsets.UTF_8).replace("+", "%20");
        nuevaVisita.setFoto(rutaURL);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      nuevaVisita.setFoto(null);
    }

    nuevaVisita.setTecnico(getSessionUser(context).getTecnico());
    nuevaVisita.setIncidente(repositorioDeIncidentes.buscarPorId(Long.valueOf(context.formParam("visita_incidente_id"))).get());
    nuevaVisita.setIncidenteSolucionado(Boolean.valueOf(context.formParam("resuelto")));
    nuevaVisita.setFecha(LocalDate.now());

    if (nuevaVisita.getIncidenteSolucionado()) {
      Incidente incidente = nuevaVisita.getIncidente();
      incidente.setEstaResuelto(true);
      incidente.getHeladera().evaluarSiTieneIndidentesNoResueltos();
      this.repositorioDeIncidentes.modificar(incidente);
    }

    this.repositorioRegistroVisitaTecnica.guardar(nuevaVisita);
    context.redirect("/visitasTecnicas/formulario_visitaTecnica");
  }

  @Override
  public void create(Context context) {
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

    renderWithUser(context, "visitasTecnicas/formulario_visitaTecnica.hbs", model);
  }

  private List<RegistroVisitaTecnica> obtenerVisitasRealizadasPorTecnico(Context context) {
    Usuario usuario = getSessionUser(context);

    if (usuario.getTecnico() == null) {
      return List.of();
    }

    List<RegistroVisitaTecnica> visitas = this.repositorioRegistroVisitaTecnica.buscarTodos();

    return visitas.stream()
        .filter(v -> v.getTecnico().equals(usuario.getTecnico()))
        .toList();
  }

  private List<RegistroVisitaTecnica> obtenerVisitasDeHeladerasPropias(Context context) {
    List<Heladera> heladerasPropias = getSessionUser(context).
        getColaborador().
        getContribucionesRealizadas().
        stream().filter(Contribucion::esHacerseCargoHeladera).
        map(Contribucion::getNuevaHeladera)
        .toList();

    List<RegistroVisitaTecnica> visitas = this.repositorioRegistroVisitaTecnica.buscarTodos();
    return visitas
        .stream()
        .filter(v -> heladerasPropias.contains(v.getIncidente().getHeladera()))
        .toList();
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
