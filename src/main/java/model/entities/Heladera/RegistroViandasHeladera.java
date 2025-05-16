package model.entities.Heladera;

import java.time.LocalDate;
import javax.persistence.CascadeType;
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
@Table(name = "registroViandasHeladera")
public class RegistroViandasHeladera extends Persistente {

  @Column(name = "fecha", nullable = false)
  private LocalDate fecha;

  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  public RegistroViandasHeladera() {
  }

  public RegistroViandasHeladera(LocalDate fecha, Integer cantidad, Heladera heladera) {
    this.fecha = fecha;
    this.cantidad = cantidad;
    this.heladera = heladera;
  }
}
