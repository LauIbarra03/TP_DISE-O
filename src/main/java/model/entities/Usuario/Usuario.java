package model.entities.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;
import model.entities.Tecnico.Tecnico;
import model.entities.TipoRol.TipoRol;

@Getter
@Setter
@Entity
@Builder
@Table(name = "usuario")
public class Usuario extends Persistente {
  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
  private Tecnico tecnico;

  @Enumerated(EnumType.STRING)
  private TipoRol tipoRol;

  public Usuario(String username, String password, Colaborador colaborador, TipoRol tipoRol) {
    this.username = username;
    this.password = password;
    this.colaborador = colaborador;
    this.tipoRol = tipoRol;
  }

  public Usuario(String username, String password, TipoRol tipoRol) {
    this.username = username;
    this.password = password;
    this.tipoRol = tipoRol;
  }

  public Usuario(String username, String password, Tecnico tecnico, TipoRol tipoRol) {
    this.username = username;
    this.password = password;
    this.tecnico = tecnico;
    this.tipoRol = tipoRol;
  }

  public Usuario(String username, String password, Colaborador colaborador, Tecnico tecnico, TipoRol tipoRol) {
    this.username = username;
    this.password = password;
    this.tecnico = tecnico;
    this.colaborador = colaborador;
    this.tipoRol = tipoRol;
  }


  public Usuario() {

  }

  public boolean isAdmin() {
    TipoRol tipoRol = this.getTipoRol();
    String tipo = String.valueOf(tipoRol);
    return "ADMIN".equals(tipo);
  }

  public boolean esHumana() {
    TipoRol tipoRol = this.getTipoRol();
    String tipo = String.valueOf(tipoRol);
    return "PERSONA_HUMANA".equals(tipo);
  }

  public boolean esJuridica() {
    TipoRol tipoRol = this.getTipoRol();
    String tipo = String.valueOf(tipoRol);
    return "PERSONA_JURIDICA".equals(tipo);
  }

  public boolean esTecnico() {
    TipoRol tipoRol = this.getTipoRol();
    String tipo = String.valueOf(tipoRol);
    return "TECNICO".equals(tipo);
  }
}
