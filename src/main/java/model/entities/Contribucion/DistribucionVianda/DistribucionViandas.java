package model.entities.Contribucion.DistribucionVianda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Contribucion.Contribucion;
import model.entities.Heladera.Heladera;
import model.entities.Vianda.Vianda;

@Getter
@Setter
@Entity
@DiscriminatorValue("distribucionViandas")
public class DistribucionViandas extends Contribucion {
  @ManyToOne
  @JoinColumn(name = "heladeraOrigen_id", referencedColumnName = "id")
  private Heladera heladeraOrigen;
  @ManyToOne
  @JoinColumn(name = "heladeraDestino_id", referencedColumnName = "id")
  private Heladera heladeraDestino;
  @OneToMany
  @JoinColumn(name = "distribucionViandas_id", referencedColumnName = "id")
  private List<Vianda> viandas;
  @Column(name = "motivo")
  private String motivo;
  @Column(name = "cantidadViandas")
  private Integer cantidadViandas;

  public DistribucionViandas(LocalDateTime fechaHora, Colaborador contribuidor, Heladera heladeraOrigen, Heladera heladeraDestino, List<Vianda> viandas, String motivo) {
    super(contribuidor, fechaHora);
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
    this.viandas = viandas;
    this.motivo = motivo;
  }

  public DistribucionViandas(LocalDateTime fechaHora, Integer cantidadViandas) {
    super( fechaHora);
    this.cantidadViandas = cantidadViandas;
  }


  public DistribucionViandas() {

  }

  @Override
  public Double calcularPuntos(Properties coeficientes) {
    double coeficiente = Double.parseDouble(coeficientes.getProperty("DISTRIBUCION_VIANDAS"));
    return viandas.isEmpty()? coeficiente*cantidadViandas : coeficiente*viandas.size();
  }
}
