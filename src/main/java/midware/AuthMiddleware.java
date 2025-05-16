package midware;

import Exceptions.AccessDeniedException;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;
import model.repositories.RepositorioUsuario;
import server.handlers.AccessDeniedHandler;


public class AuthMiddleware {
  public static void apply(Javalin app) {
    AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandler();
    accessDeniedHandler.setHandle(app);
    app.beforeMatched(ctx -> {
      validarRoles(ctx);

      validarAccesoAPerfil(ctx);
    });
  }

  private static TipoRol getUserRoleType(Context context) {
    Long user_id = context.sessionAttribute("user_id");
    if (user_id == null) return null;
    Usuario user = new RepositorioUsuario().buscarPorID(user_id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    return user.getTipoRol();
  }

  private static void validarAccesoAPerfil(Context context) {
    String currentPath = context.path();
    if (currentPath.startsWith("/perfil/")) {
      Long user_id = context.sessionAttribute("user_id");
      Usuario user = new RepositorioUsuario().buscarPorID(user_id)
          .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

      var id = user.getColaborador() != null ? user.getColaborador().getId() : user.getTecnico().getId();
      String pathParamId = context.pathParam("id");

      if (pathParamId != null && !pathParamId.isEmpty()) {
        Long perfilId = Long.valueOf(pathParamId);


        if (!id.equals(perfilId) && !getUserRoleType(context).equals(TipoRol.ADMIN)) {
          throw new AccessDeniedException();
        }
      }
    }
  }

  private static void validarRoles(Context context) {
    var userRole = getUserRoleType(context);
    if (!context.routeRoles().isEmpty() && !context.routeRoles().contains(userRole)) {
      throw new AccessDeniedException();
    }
  }
}
