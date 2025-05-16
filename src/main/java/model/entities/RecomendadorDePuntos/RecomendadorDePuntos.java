package model.entities.RecomendadorDePuntos;

import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import model.entities.RecomendadorDePuntos.entities.ErrorRecomendador;
import model.entities.RecomendadorDePuntos.entities.ListadoDePuntos;

@Getter
@Setter
public class RecomendadorDePuntos {
  private RecomendadorPuntosAdapter recomendadorPuntosAdapter;

  public RecomendadorDePuntos(RecomendadorPuntosAdapter recomendadorPuntosAdapter) {
    this.recomendadorPuntosAdapter = recomendadorPuntosAdapter;
  }

  public ListadoDePuntos puntoDeColocacionRecomendados(double latitud, double longitud, Integer radio) throws IOException {
    return recomendadorPuntosAdapter.puntoDeColocacionRecomendados(latitud, longitud, radio);
  }

  public ErrorRecomendador puntoDeColocacionRecomendados(double latitud, double longitud) throws IOException {
    return recomendadorPuntosAdapter.puntoDeColocacionRecomendados(latitud, longitud);
  }

  public ErrorRecomendador puntoDeColocacionRecomendados() throws IOException {
    return recomendadorPuntosAdapter.puntoDeColocacionRecomendados();
  }
}
