package model.entities.Vianda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "comida")
public class Comida extends Persistente {
  @Column(name = "nombre")
  private String nombre;

  public Comida() {
  }

  public Comida(String nombre) {
    this.nombre = nombre;
  }
}