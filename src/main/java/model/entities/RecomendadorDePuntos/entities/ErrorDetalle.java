package model.entities.RecomendadorDePuntos.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetalle {
  public int codigo;
  public String mensaje;
  private List<Detalle> detalles;

  @Getter
  @Setter
  public class Detalle {
    public String tipo;
    public String mensaje;
  }
}
