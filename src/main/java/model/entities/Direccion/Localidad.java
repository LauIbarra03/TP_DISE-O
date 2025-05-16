package model.entities.Direccion;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.Tecnico.Tecnico;

@Getter
@Setter
@Entity
@Table(name = "localidad")
public class Localidad extends Persistente {
  @Column(name = "nombre", nullable = false)
  private String nombre;

  @ManyToOne
  @JoinColumn(name = "provincia_id", referencedColumnName = "id")
  private Provincia provincia;

  @ManyToMany()
  @JoinTable(
      name = "localidad_tecnico",
      joinColumns = @JoinColumn(name = "localidad_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "tecnico_id", referencedColumnName = "id")
  )
  private Set<Tecnico> tecnicos = new HashSet<>();

  public Localidad() {
  }

  public Localidad(String nombre, Provincia provincia) {
    this.nombre = nombre;
    this.provincia = provincia;
  }

  public void agregarTecnico(Tecnico tecnico) {
    tecnicos.add(tecnico);
  }


}
