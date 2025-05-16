package model.entities.MedioDeContacto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "medioDeContacto")
public class MedioDeContacto extends Persistente {
  @Enumerated(EnumType.STRING)
  @Column(name = "tipoContacto")
  private TipoContacto tipoContacto;
  @Column(name = "contacto")
  private String contacto;

  public MedioDeContacto(TipoContacto tipoContacto, String contacto) {
    this.tipoContacto = tipoContacto;
    this.contacto = contacto;
  }

  public String getEmail() {
    if (this.tipoContacto == TipoContacto.CORREO) {
      return this.contacto;
    }
    return null;
  }

  public MedioDeContacto() {

  }
}