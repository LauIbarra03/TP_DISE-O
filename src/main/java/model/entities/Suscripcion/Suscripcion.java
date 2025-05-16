package model.entities.Suscripcion;

import java.io.IOException;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;
import model.entities.Heladera.Heladera;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.Notificador.Notificador;

@Getter
@Setter
@Entity
@Table(name = "suscripcion")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion extends Persistente {
  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  public Heladera heladera;
  @OneToOne
  @JoinColumn(name = "medioDeContacto", referencedColumnName = "id")
  public MedioDeContacto medioDeContacto;
  @Transient
  public Notificador notificador = Notificador.getInstance();
  @Transient
  private List<RegistroMensaje> mensajesEnviados;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tipoSuscripcion_id", referencedColumnName = "id")
  private TipoSuscripcion tipoSuscripcion;
  @ManyToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;


  public Suscripcion(Heladera heladera, MedioDeContacto medioDeContacto, TipoSuscripcion tipoSuscripcion) {
    this.heladera = heladera;
    this.medioDeContacto = medioDeContacto;
    this.tipoSuscripcion = tipoSuscripcion;
  }

  public String generarMensaje(String tipoSuscripcion) {

    String mensaje = "Problema del tipo: " + tipoSuscripcion + ", " +
        "en la heladera localizada en la calle: " +
        this.heladera.calle().getNombre() + " " + heladera.altura();
    return mensaje;
  }

  public void llamarNotificador() throws IOException {
    notificador.notificar(medioDeContacto, this.generarMensaje(tipoSuscripcion.getClass().getSimpleName()) + tipoSuscripcion.mensajeExtra(heladera));
  }

  public Boolean cumpleCondicion() throws IOException {
    return tipoSuscripcion.cumpleCondicion(heladera);
  }

  public void agregarRegistroMensaje(RegistroMensaje registroMensaje) {
    this.mensajesEnviados.add(registroMensaje);
  }

}
