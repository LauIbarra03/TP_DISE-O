package model.entities.Producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "rubro")
public class Rubro extends Persistente {
  @Column(name = "rubro")
  private String rubro;

  public Rubro(String rubro) {
    this.rubro = rubro;
  }

  public Rubro() {

  }
}
