package model.entities.Heladera.SolicitudSobreHeladera;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.Contribucion.Contribucion;
import model.entities.Heladera.Heladera;
import model.entities.Tarjeta.Tarjeta;

@Getter
@Setter
@Entity
@Table(name = "solicitudSobreHeladera")
public class SolicitudSobreHeladera extends Persistente {

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)  // relación con Heladera
  private Heladera heladera;

  @ManyToOne
  @JoinColumn(name = "contribucion_id")
  private Contribucion accion;

  @ManyToOne
  @JoinColumn(name = "tarjeta_id")
  private Tarjeta tarjetaColaborador;

  @Column(name = "fecha_y_hora", columnDefinition = "TIMESTAMP")
  private LocalDateTime fechaYHora;

  @Column(name = "registro_apertura", columnDefinition = "TIMESTAMP")
  private LocalDateTime registroApertura;

  public SolicitudSobreHeladera() {
  }

  public SolicitudSobreHeladera(Contribucion accion, Tarjeta colaborador, LocalDateTime fechaYHora) {
    this.accion = accion;
    this.tarjetaColaborador = colaborador;
    this.fechaYHora = fechaYHora;
    this.registroApertura = null;
  }

}
