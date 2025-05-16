package controllers;

import io.javalin.http.Context;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioUsuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utils.ICrudViewsHandler;

public class AuthController implements ICrudViewsHandler {
  private static final Logger logger = Logger.getLogger(AuthController.class.getName());
  private final RepositorioUsuario repositorioUsuario;

  public AuthController(RepositorioUsuario repositorioUsuario) {
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  public void index(Context context) {

    Map<String, Object> model = new HashMap<>();

    model.put("titulo", "Login");

    context.render("login/index.hbs", model);
  }

  public void indexLogin(Context context) {
    Map<String, Object> model = new HashMap<>();

    model.put("titulo", "Login");

    context.render("login/login.hbs", model);
  }


  public void procesarLogin(Context context) {

    String username = context.formParam("usuario");
    String password = context.formParam("password");

    List<Usuario> usuariosEncontrados = this.repositorioUsuario.busacrPorUsuario(username);
    Usuario user = null;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    for (Usuario userIteracion : usuariosEncontrados) {
      // Para los usuarios precargados
      if (passwordEncoder.matches(password, userIteracion.getPassword())) {
        user = userIteracion;
        break;
      }
    }

    if (user != null) {
      logger.info("Se ha loggeado el usuario de id " + user.getId() + " desde la ip " + context.ip());
      context.sessionAttribute("user_id", user.getId());
      context.redirect("/panel");
    } else {
      String error = "Credenciales incorrectas";
      logger.info("Credenciales incorrectas: Se ha intentado ingresar al usuario " + username + " con la contraseña " + password + " desde la ip " + context.ip());
      context.render("login/login.hbs", Map.of("error", error));
    }
  }

  public void logout(Context context) {
    logger.info("Se ha desloggeado el usuario de id " + context.sessionAttribute("user_id") + " desde la ip " + context.ip());
    context.sessionAttribute("user_id", null);
    context.cookie("JSESSIONID", "", 0);
    context.redirect("/");
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {

  }

  @Override
  public void save(Context context) throws IOException {

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
