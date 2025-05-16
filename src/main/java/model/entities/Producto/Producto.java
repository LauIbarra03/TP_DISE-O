package model.entities.Producto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Colaborador.Persistente;

@Setter
@Getter
@Entity
@Table(name = "producto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends Persistente {
  @Column(name = "nombre")
  private String nombre;
  @ManyToOne
  @JoinColumn(name = "producto_id", referencedColumnName = "id")
  private Rubro rubro;
  @Column(name = "cantidadDePuntos")
  private Integer cantidadDePuntos;
  @Column(name = "imagenIlustrativa")
  private String imagenIlustrativa;

}
