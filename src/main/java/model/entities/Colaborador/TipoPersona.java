package model.entities.Colaborador;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tipoPersona")
public class TipoPersona extends Persistente {
  @Column(name = "nombre")
  private String nombre;
  @Enumerated(EnumType.STRING)
  @ElementCollection()
  @CollectionTable(name = "formasDeContribuir", joinColumns = @JoinColumn(name = "tipoPersona_id", referencedColumnName = "id"))
  @Column(name = "formaDeContribucion")
  private List<FormaDeContribucion> formasDeContribuir;

  public TipoPersona(String nombre, List<FormaDeContribucion> formasDeContribuir) {
    this.nombre = nombre;
    this.formasDeContribuir = formasDeContribuir;
  }

  public TipoPersona() {

  }
}
