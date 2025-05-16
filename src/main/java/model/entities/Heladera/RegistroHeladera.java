package model.entities.Heladera;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Builder
@Table(name = "registroHeladera")
public class RegistroHeladera extends Persistente {

  @Column(name = "fecha", nullable = false)
  private LocalDate fecha;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false)
  private EstadoHeladera estado;

  @ManyToOne
  @JoinColumn(name = "heladera_id", nullable = false)
  private Heladera heladera;

  public RegistroHeladera() {
  }

  public RegistroHeladera(LocalDate fecha, EstadoHeladera estado, Heladera heladera) {
    this.fecha = fecha;
    this.estado = estado;
    this.heladera = heladera; // para establecer la relación con heladera
  }

  public RegistroHeladera(LocalDate of, EstadoHeladera estadoHeladera) {
  } // creo el constructor porque sino el puntos test rompe
}
