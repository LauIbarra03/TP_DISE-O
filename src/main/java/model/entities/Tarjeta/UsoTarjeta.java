package model.entities.Tarjeta;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;
import model.entities.Heladera.Heladera;
import model.entities.PersonaVulnerable.PersonaVulnerable;

@Getter
@Entity
@Table(name = "usoTarjeta")
public class UsoTarjeta extends Persistente {
  @Column(name = "fechaHora")
  private LocalDateTime fechaHora;
  @ManyToOne
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;
  @ManyToOne
  @JoinColumn(name = "usuarioVulnerable_id", referencedColumnName = "id")
  private PersonaVulnerable usuarioVulnerable;
  @ManyToOne
  @JoinColumn(name = "usuarioColaborador_id", referencedColumnName = "id")
  private Colaborador usuarioColaborador;

  public UsoTarjeta(LocalDateTime fechaHora, Heladera heladera, PersonaVulnerable usuarioVulnerable) {
    this.fechaHora = fechaHora;
    this.heladera = heladera;
    this.usuarioVulnerable = usuarioVulnerable;
  }

  public UsoTarjeta(LocalDateTime fechaHora, Heladera heladera, Colaborador usuarioColaborador) {
    this.fechaHora = fechaHora;
    this.heladera = heladera;
    this.usuarioColaborador = usuarioColaborador;
  }
}
