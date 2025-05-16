package model.entities.Tecnico;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.Incidente.Incidente;

@Setter
@Getter
@Entity
@Builder
@Table(name = "registroVisitaTecnica")
public class RegistroVisitaTecnica extends Persistente {

  @Column(name = "fecha", columnDefinition = "DATE")
  private LocalDate fecha;

  @Column(name = "incidenteSolucionado", columnDefinition = "BOOLEAN")
  private Boolean incidenteSolucionado;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "foto")
  private String foto;

  @ManyToOne
  @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
  private Tecnico tecnico;

  @ManyToOne
  @JoinColumn(name = "incidente_id", referencedColumnName = "id") // relación con Incidente
  private Incidente incidente;

  public RegistroVisitaTecnica() {
  }

  public RegistroVisitaTecnica(LocalDate fecha, Boolean incidenteSolucionado, String descripcion, String foto, Tecnico tecnico, Incidente incidente) {
    this.fecha = fecha;
    this.incidenteSolucionado = incidenteSolucionado;
    this.descripcion = descripcion;
    this.foto = foto;
    this.tecnico = tecnico;
    this.incidente = incidente;
  }
}


