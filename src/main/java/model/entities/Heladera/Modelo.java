package model.entities.Heladera;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Builder
@Entity
@Table(name = "modelo")
public class Modelo extends Persistente {
  @Column(name = "nombre")
  private String nombre;

  @Column(name = "tempMaxima")
  private Integer tempMaxima;

  @Column(name = "tempMinima")
  private Integer tempMinima;

  public Modelo(String nombre, Integer tempMaxima, Integer tempMinima) {
    this.nombre = nombre;
    this.tempMaxima = tempMaxima;
    this.tempMinima = tempMinima;
  }

  public Modelo() {
  }
}
