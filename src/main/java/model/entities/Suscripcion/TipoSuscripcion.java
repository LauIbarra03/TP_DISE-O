package model.entities.Suscripcion;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import model.entities.Colaborador.Persistente;
import model.entities.Heladera.Heladera;

@Entity
@Table(name = "tipoSuscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TipoSuscripcion extends Persistente {
  public abstract Boolean cumpleCondicion(Heladera heladera);

  public abstract String mensajeExtra(Heladera heladera);

  public String getTipo() {
    return this.getClass().getAnnotation(DiscriminatorValue.class).value();
  }

  public boolean esMuchasViandas() {
    return this.getTipo().equals("muchasViandas");
  }

  public boolean esPocasViandas() {
    return this.getTipo().equals("pocasViandas");
  }

  public boolean esDesperfectoHeladera() {
    return this.getTipo().equals("desperfectoHeladera");
  }
}
