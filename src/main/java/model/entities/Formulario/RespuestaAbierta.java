package model.entities.Formulario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "respuestaAbierta")
public class RespuestaAbierta extends Persistente {

  @Column(name = "respuesta", columnDefinition = "TEXT")
  private String respuesta;

  @ManyToOne
  @JoinColumn(name = "preguntaRespondida_id", referencedColumnName = "id")
  private Pregunta preguntaRespondida;

  public RespuestaAbierta() {
  }

  public RespuestaAbierta(String respuesta, Pregunta preguntaRespondida) {
    this.respuesta = respuesta;
    this.preguntaRespondida = preguntaRespondida;
  }
}