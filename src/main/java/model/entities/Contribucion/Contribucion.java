package model.entities.Contribucion;

import java.time.LocalDateTime;
import java.util.Properties;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;
import model.entities.Heladera.Heladera;
import model.entities.PersonaVulnerable.PersonaVulnerable;
import model.entities.Producto.Producto;
import model.entities.Vianda.Vianda;

@Getter
@Setter
@Entity
@Table(name = "contribucion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Contribucion extends Persistente {

  @ManyToOne(cascade =  CascadeType.ALL)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;
  @Column(name = "fechaHora")
  private LocalDateTime fechaHora;

  @ManyToOne
  @JoinColumn(name = "nueva_heladera_id", referencedColumnName = "id")
  private Heladera nuevaHeladera;

  @ManyToOne
  @JoinColumn(name = "nueva_vianda_id", referencedColumnName = "id")
  private Vianda nuevaVianda;

  @ManyToOne
  @JoinColumn(name = "nueva_producto_id", referencedColumnName = "id")
  private Producto nuevaProducto;

  @ManyToOne
  @JoinColumn(name = "nuevo_vulnerable_id", referencedColumnName = "id")
  private PersonaVulnerable nuevoVulnerable;

  public Contribucion(Colaborador colaborador, LocalDateTime fechaHora) {
    this.colaborador = colaborador;
    this.fechaHora = fechaHora;
  }
  public Contribucion(LocalDateTime fechaHora) {
    this.fechaHora = fechaHora;
  }

  public Contribucion() {

  }

  public String getTipo() {
    // Aquí se asume que el campo 'tipo' es un atributo de la clase
    // Si no lo es, puedes usar el método que proporciona JPA para obtenerlo.
    return this.getClass().getAnnotation(DiscriminatorValue.class).value();
  }

  public boolean esDistribucionViandas() {
    return this.getTipo().equals("distribucionViandas");
  }

  public boolean esDonacionDinero() {
    return this.getTipo().equals("donacionDinero");
  }

  public boolean esRegistrarPersonaVulnerable() {
    return this.getTipo().equals("registrarPersonaVulnerable");
  }

  public boolean esRealizarOfertas() {
    return this.getTipo().equals("realizarOfertas");
  }

  public boolean esHacerseCargoHeladera() {
    return this.getTipo().equals("hacerseCargoHeladera");
  }

  public boolean esDonacionVianda() {
    return this.getTipo().equals("donacionVianda");
  }

  public abstract Double calcularPuntos(Properties coeficientes);


}
