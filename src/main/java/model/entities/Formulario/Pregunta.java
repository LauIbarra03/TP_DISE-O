package model.entities.Formulario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "pregunta")
public class Pregunta extends Persistente {

  @Column(name = "enunciado", columnDefinition = "TEXT", nullable = false)
  private String enunciado;

  public Pregunta() {
  }

  public Pregunta(String enunciado) {
    this.enunciado = enunciado;
  }

  public String getTexto() {
    return null;
  }

  public void setTexto(String s) {
  }
}