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
@Table(name = "ciudad")
public class Ciudad extends Persistente {
  @Column(name = "nombre")
  private String nombre;

  public Ciudad() {
  }

  public Ciudad(String nombre) {
    this.nombre = nombre;
  }
}
