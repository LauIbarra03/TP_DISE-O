package controllers;

import io.javalin.http.Context;
import java.io.IOException;
import java.util.Map;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioUsuario;
import utils.ICrudViewsHandler;

public class BaseController implements ICrudViewsHandler {
  protected RepositorioUsuario repositorioUsuario;

  public BaseController(RepositorioUsuario repositorioUsuario) {
    this.repositorioUsuario = repositorioUsuario;
  }

  public BaseController() {
  }

  public void validarSesion(Context context) {
    Long user_id = context.sessionAttribute("user_id");
    if (user_id == null) {
      context.redirect("/");
    }
  }

  public void renderWithUser(Context context, String template, Map<String, Object> model) {
    validarSesion(context);

    Usuario user = getSessionUser(context);
    model.put("user", user);
    context.render(template, model);
  }

  public Usuario getSessionUser(Context context) {
    Long user_id = context.sessionAttribute("user_id");
    if (user_id == null) return null;

    Usuario user = repositorioUsuario.buscarPorID(user_id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    return user;
  }

  @Override
  public void index(Context context) {

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
