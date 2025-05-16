package model.entities.TipoRol;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
  ADMIN,
  PERSONA_HUMANA,
  PERSONA_JURIDICA,
  TECNICO
}
