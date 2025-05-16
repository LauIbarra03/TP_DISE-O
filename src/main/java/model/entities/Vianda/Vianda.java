package model.entities.Vianda;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;
import model.entities.Heladera.Heladera;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "vianda")
public class Vianda extends Persistente {

  @ManyToOne
  @JoinColumn(name = "comida_id", referencedColumnName = "id")
  private Comida comida;

  @Column(name = "fechaCaducidad", columnDefinition = "DATE")
  private LocalDate fechaCaducidad;

  @Column(name = "fechaDonacion", columnDefinition = "DATE")
  private LocalDate fechaDonacion;

  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @Column(name = "calorias")
  private Double calorias;

  @Column(name = "peso")
  private Double peso;

  @Enumerated(EnumType.STRING)
  private EstadoVianda estadoVianda;

  public Vianda() {
  }

  public Vianda(Comida comida, LocalDate fechaCaducidad, LocalDate fechaDonacion, Colaborador colaborador, Heladera heladera, EstadoVianda estadoVianda) {
    this.comida = comida;
    this.fechaCaducidad = fechaCaducidad;
    this.fechaDonacion = fechaDonacion;
    this.colaborador = colaborador;
    this.heladera = heladera;
    // this.calorias = 0; // Dato opcional
    // this.peso = 0; // Dato opcional
    this.estadoVianda = estadoVianda;
  }

  public boolean isEntregada() {
    return estadoVianda == EstadoVianda.Entregada;
  }
}