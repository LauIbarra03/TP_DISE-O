package model.entities.Formulario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import model.entities.Colaborador.Persistente;

@Getter
@Entity
@Table(name = "opcion")
public class Opcion extends Persistente {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  public Opcion() {
  }

  public Opcion(String nombre) {
    this.nombre = nombre;
  }
}
