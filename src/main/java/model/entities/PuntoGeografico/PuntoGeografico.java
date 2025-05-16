package model.entities.PuntoGeografico;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;

@Getter
@Setter
@Entity
@Table(name = "puntoGeografico")
public class PuntoGeografico extends Persistente {
  @Column(name = "longitud")
  private Float longitud;
  @Column(name = "latitud")
  private Float latitud;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "direccion_id", referencedColumnName = "id")
  private Direccion direccion;
  @Column(name = "nombre")
  private String nombre;

  public PuntoGeografico(Float longitud, Float latitud, Direccion direccion, String nombre) {
    this.longitud = longitud;
    this.latitud = latitud;
    this.direccion = direccion;
    this.nombre = nombre;
  }

  public PuntoGeografico() {

  }


  public Calle calle() {
    return direccion.getCalle();
  }

  public Integer altura() {
    return direccion.getAltura();
  }

  public Localidad localidad() {
    return direccion.getLocalidad();
  }
}