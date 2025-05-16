package server.handlers;

import Exceptions.AccessDeniedException;
import io.javalin.Javalin;

public class AccessDeniedHandler implements IHandler {

  @Override
  public void setHandle(Javalin app) {
    app.exception(AccessDeniedException.class, (e, context) -> {
      context.status(403);
      context.render("errores/403.hbs");
    });
  }
}
