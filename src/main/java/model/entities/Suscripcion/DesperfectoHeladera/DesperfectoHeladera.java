package model.entities.Suscripcion.DesperfectoHeladera;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Suscripcion.TipoSuscripcion;

@Entity
@DiscriminatorValue("desperfectoHeladera")
public class DesperfectoHeladera extends TipoSuscripcion {
  @Transient
  private List<Heladera> heladeraSugeridas;

  public DesperfectoHeladera() {
  }

  public String mensajeExtra(Heladera heladera) {
    StringBuilder mensaje = new StringBuilder();
    Integer i = 1;
    mensaje.append("\n\nListado de heladeras recomendadas: \n");
    for (Heladera h : heladeraSugeridas) {
      mensaje
          .append("Heladera " + i + ") ")
          .append(h.calle().getNombre() + " " + h.altura())
          .append("\n");
      i++;
    }

    return mensaje.toString();
  }

  @Override
  public Boolean cumpleCondicion(Heladera heladera) {
    return heladera.getEstadoHeladera() == EstadoHeladera.NO_ACTIVA;
  }

}
