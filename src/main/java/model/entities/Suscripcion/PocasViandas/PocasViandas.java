package model.entities.Suscripcion.PocasViandas;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import model.entities.Heladera.Heladera;
import model.entities.Suscripcion.TipoSuscripcion;

@Getter
@Entity
@DiscriminatorValue("pocasViandas")
public class PocasViandas extends TipoSuscripcion {
  @Column(name = "valorPocasViandas")
  private Integer valorPocasViandas;

  public PocasViandas(Integer valorPocasViandas) {
    this.valorPocasViandas = valorPocasViandas;
  }

  public PocasViandas() {
  }

  public Boolean cumpleCondicion(Heladera heladera) {
    return heladera.getCantViandas() <= valorPocasViandas;
  }

  public String mensajeExtra(Heladera heladera) {
    return "";
  }
}
