package model.entities.Contribucion.DonacionVianda;

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
import model.entities.Vianda.Vianda;

@Getter
@Setter
@Entity
@DiscriminatorValue("donacionVianda")
public class DonacionVianda extends Contribucion {
  @OneToMany
  @JoinColumn(name = "donacionVianda_id", referencedColumnName = "id")
  private List<Vianda> viandas = new ArrayList<>();

  public DonacionVianda(Colaborador contribuidor, LocalDateTime fechaHora, List<Vianda> viandas) {
    super(contribuidor, fechaHora);
    this.viandas = viandas;
  }

  public DonacionVianda( LocalDateTime fechaHora) {
    super(fechaHora);
  }

  public DonacionVianda() {
    viandas = new ArrayList<>();
  }

  public void agregarVianda(Vianda vianda) {
    viandas.add(vianda);
  }

  @Override
  public Double calcularPuntos(Properties coeficientes) {
    double coeficiente = Double.parseDouble(coeficientes.getProperty("DONACION_VIANDAS"));
    return viandas.isEmpty() ? coeficiente : coeficiente * viandas.size();
  }
}