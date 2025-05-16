package model.entities.Suscripcion;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.Usuario.Usuario;

@Setter
@Getter
@Entity
@Table(name = "RegistroMensaje")
public class RegistroMensaje extends Persistente {
  @Column(name = "mensaje")
  private String mensaje;

  @Column(name = "fechaHora")
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "medioContacto_id", referencedColumnName = "id")
  private MedioDeContacto medioDeContacto;

  public RegistroMensaje(String mensaje, LocalDateTime fechaHora) {
    this.mensaje = mensaje;
    this.fechaHora = fechaHora;
  }

  public RegistroMensaje() {
    this.fechaHora = LocalDateTime.now();
  }
}
