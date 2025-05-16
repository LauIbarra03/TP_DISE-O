package model.entities.Tecnico;

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
import model.entities.Colaborador.Persistente;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.Utils.TipoDocumento;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tecnico")
public class Tecnico extends Persistente {
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "apellido", nullable = false)
  private String apellido;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipoDocumento")
  private TipoDocumento tipoDocumento;

  @Column(name = "nroDocumento")
  private String nroDocumento;

  @Column(name = "cuil")
  private String cuil;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "medioDeContacto_id", referencedColumnName = "id")
  private MedioDeContacto medioDeContacto;


  public Tecnico() {
  }


  public Tecnico(String nombre, String apellido, TipoDocumento tipoDocumento, String nroDocumento, String cuil, MedioDeContacto medioDeContacto) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipoDocumento = tipoDocumento;
    this.nroDocumento = nroDocumento;
    this.cuil = cuil;
    this.medioDeContacto = medioDeContacto;
  }
}
