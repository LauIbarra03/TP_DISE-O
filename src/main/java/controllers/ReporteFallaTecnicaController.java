package controllers;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Incidente.Incidente;
import model.entities.Incidente.TipoIncidente;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioDeIncidentes;
import model.repositories.RepositorioHeladera;
import model.repositories.RepositorioUsuario;

public class ReporteFallaTecnicaController extends BaseController {
  private final RepositorioHeladera repositorioHeladera;
  private final RepositorioDeIncidentes repositorioDeIncidentes;

  public ReporteFallaTecnicaController(RepositorioHeladera repositorioHeladera, RepositorioDeIncidentes repositorioDeIncidentes, RepositorioUsuario repositorioUsuario) {
    super(repositorioUsuario);
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioDeIncidentes = repositorioDeIncidentes;
  }

  @Override
  public void index(Context context) {

    List<Heladera> heladeras = this.repositorioHeladera.buscarTodos();
    Map<String, Object> model = new HashMap<>();

    model.put("heladeras", heladeras);
    model.put("titulo", "Reporte falla tecnica");

    renderWithUser(context, "reporte_falla_tecnica.hbs", model);
  }

  private Incidente getNuevaReporte(Context context) throws IOException {
    Incidente nuevoIncidente = new Incidente();
    nuevoIncidente.setTipoIncidente(TipoIncidente.FallaTecnica);
    nuevoIncidente.setEstaResuelto(false);

    String heladera_id_str = context.formParam("heladera_id");
    String descripcion_str = context.formParam("descripcion_falla_tecnica");

    Long heladera_id = Long.valueOf(heladera_id_str);
    Heladera heladera = this.repositorioHeladera.buscarPorID(heladera_id)
        .orElseThrow(() -> new IllegalArgumentException("Heladera no encontrada"));

    UploadedFile archivo = context.uploadedFile("falla_tecnica_foto");

    if (archivo != null && archivo.size() > 0) {
      String directorioDestino = "src/main/resources/public/fotos_reportes/";

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

        String rutaURL = "/fotos_reportes/" + URLEncoder.encode(nombreOriginal, StandardCharsets.UTF_8).replace("+", "%20");
        nuevoIncidente.setFoto(rutaURL);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      nuevoIncidente.setFoto(null);
    }

    Usuario usuario = getSessionUser(context);

    nuevoIncidente.setColaborador(usuario.getColaborador());
    nuevoIncidente.setDescripcion(descripcion_str);
    nuevoIncidente.setHeladera(heladera);
    nuevoIncidente.setFechaYHora(LocalDateTime.now());
    heladera.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
    heladera.agregarIncidente(nuevoIncidente);
    return nuevoIncidente;
  }


  @Override
  public void save(Context context) throws IOException {
    Incidente nuevoIncidente = getNuevaReporte(context);

    this.repositorioDeIncidentes.guardar(nuevoIncidente);
    context.redirect("/reportar_falla_tecnia");
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) throws IOException {

  }

  @Override
  public void delete(Context context) {

  }
}
