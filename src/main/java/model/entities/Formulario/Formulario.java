package model.entities.Formulario;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "formulario")
public class Formulario extends Persistente {

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "formulario_id")
  private List<Pregunta> preguntas;

  public Formulario() {
  }

  public Formulario(String nombre, List<Pregunta> preguntas) {
    this.nombre = nombre;
    this.preguntas = preguntas;
  }

  public void setActivo(boolean b) {
  }
}
