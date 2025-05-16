package model.entities.Suscripcion.MuchasViandas;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import model.entities.Heladera.Heladera;
import model.entities.Suscripcion.TipoSuscripcion;

@Getter
@Entity
@DiscriminatorValue("muchasViandas")
public class MuchasViandas extends TipoSuscripcion {
  @Column(name = "valorMuchasViandas")
  private Integer valorMuchasViandas;

  public MuchasViandas(Integer valorMuchasViandas) {
    this.valorMuchasViandas = valorMuchasViandas;
  }

  public MuchasViandas() {
  }
//    @Override
//    public void avisarNotificador() throws IOException {
//        if ( this.cumcumpleCondicion() ) {
//            notificador.notificar(medioDeContacto, generarMensaje("MUCHAS_VIANDAS"));
//        }
//    }

  public Boolean cumpleCondicion(Heladera heladera) {
    return heladera.getCantViandas() >= valorMuchasViandas;
  }


  public String mensajeExtra(Heladera heladera) {
    return "";
  }
}
