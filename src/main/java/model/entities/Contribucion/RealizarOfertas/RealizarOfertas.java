package model.entities.Contribucion.RealizarOfertas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Contribucion.Contribucion;
import model.entities.Producto.Producto;

@Getter
@Setter
@Entity
@DiscriminatorValue("realizarOfertas")
public class RealizarOfertas extends Contribucion {
  @OneToMany
  @JoinColumn(name = "realizarOfertas_id", referencedColumnName = "id")
  private List<Producto> productos = new ArrayList<>();

  public RealizarOfertas(Colaborador contribuidor, LocalDateTime fechaHora, List<Producto> productos) {
    super(contribuidor, fechaHora);
    this.productos = productos;
  }

  public RealizarOfertas() {
    this.productos = new ArrayList<>();
  }

  public void agregarProducto(Producto producto) {
    this.productos.add(producto);
  }

  @Override
  public Double calcularPuntos(Properties coeficientes) {
    return 0.0;
  }
}
