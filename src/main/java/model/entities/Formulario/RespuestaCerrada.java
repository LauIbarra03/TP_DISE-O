package model.entities.Formulario;

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
@Table(name = "respuestaCerrada")
public class RespuestaCerrada extends Persistente {

  @ManyToOne
  @JoinColumn(name = "opcionElegida_id", referencedColumnName = "id")
  private Opcion respuesta;

  @ManyToOne
  @JoinColumn(name = "preguntaRespondida_id", referencedColumnName = "id")
  private Pregunta preguntaRespondida;

  public RespuestaCerrada() {
  }

  public RespuestaCerrada(Opcion respuesta, Pregunta preguntaRespondida) {
    this.respuesta = respuesta;
    this.preguntaRespondida = preguntaRespondida;
  }
}