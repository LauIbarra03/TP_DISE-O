package model.entities.Direccion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "calle")
public class Calle extends Persistente {
  @Column(name = "nombre", nullable = false)
  private String nombre;

  public Calle() {
  }

  public Calle(String nombre) {
    this.nombre = nombre;
  }
}
