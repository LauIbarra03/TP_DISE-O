package model.entities.Direccion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Getter
@Setter
@Entity
@Table(name = "direccion")
@Builder
public class Direccion extends Persistente {
  @ManyToOne
  @JoinColumn(name = "calle_id", referencedColumnName = "id")
  private Calle calle;

  @Column(name = "altura")
  private Integer altura;

  @Column(name = "codigoPostal")
  private String codigoPostal;

  @ManyToOne
  @JoinColumn(name = "ciudad_id", referencedColumnName = "id")
  private Ciudad ciudad;

  @ManyToOne
  @JoinColumn(name = "localidad_id", referencedColumnName = "id")
  private Localidad localidad;

  public Direccion() {
  }

  public Direccion(Calle calle, Integer altura, String codigoPostal, Ciudad ciudad, Localidad localidad) {
    this.calle = calle;
    this.altura = altura;
    this.codigoPostal = codigoPostal;
    this.ciudad = ciudad;
    this.localidad = localidad;
  }

  public String calle() {
    return this.calle.getNombre();
  }

  public String toString() {
    return "Direccion{" +
        "calle=" + calle +
        ", altura=" + altura +
        ", codigoPostal=" + codigoPostal +
        ", ciudad=" + ciudad +
        ", provincia=" + localidad.getProvincia() +
        ", localidad=" + localidad +
        '}';
  }

  public String show() {
    return getCalle().getNombre() + " " + getAltura() + ", " + getCodigoPostal() + ", " + localidad.getNombre() + ", " + localidad.getProvincia().getNombre();
  }

  public boolean estaVacia() {
    return (this.calle == null || this.calle.getNombre() == null || this.calle.getNombre().isEmpty())
        && this.altura == null
        && this.codigoPostal == null
        && this.ciudad == null
        && this.localidad == null;
  }

  public void setActivo(boolean b) {
  }
}