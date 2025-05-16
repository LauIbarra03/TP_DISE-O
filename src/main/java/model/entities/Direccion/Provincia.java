package model.entities.Direccion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Setter
@Getter
@Entity
@Table(name = "provincia")
public class Provincia extends Persistente {
  @Column(name = "nombre", nullable = false)
  private String nombre;

  public Provincia() {
  }

  public Provincia(String nombre) {
    this.nombre = nombre;
  }
}
