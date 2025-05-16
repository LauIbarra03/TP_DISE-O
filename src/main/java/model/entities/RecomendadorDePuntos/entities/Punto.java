package model.entities.RecomendadorDePuntos.entities;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Punto {
  public double latitud;
  public double longitud;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Punto punto = (Punto) o;
    return Double.compare(punto.latitud, latitud) == 0 &&
        Double.compare(punto.longitud, longitud) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(latitud, longitud);
  }
}
