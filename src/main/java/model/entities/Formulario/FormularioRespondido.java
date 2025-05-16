package model.entities.Formulario;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "formularioRespondido")
public class FormularioRespondido extends Persistente {

  @OneToOne
  @JoinColumn(name = "formulario_id", referencedColumnName = "id")
  private Formulario formulario;

  @OneToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador usuario;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "respuestaAbierta_id", referencedColumnName = "id")
  private List<RespuestaAbierta> respuestasAbiertas;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "respuestaCerrada_id", referencedColumnName = "id")
  private List<RespuestaCerrada> respuestasCerradas;

  public FormularioRespondido() {
  }

  public FormularioRespondido(Formulario formulario, Colaborador usuario, List<RespuestaAbierta> respuestasAbiertas, List<RespuestaCerrada> respuestasCerradas) {
    this.formulario = formulario;
    this.usuario = usuario;
    this.respuestasAbiertas = respuestasAbiertas;
    this.respuestasCerradas = respuestasCerradas;
  }
}
