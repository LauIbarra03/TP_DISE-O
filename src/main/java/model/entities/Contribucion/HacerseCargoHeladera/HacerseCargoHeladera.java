package model.entities.Contribucion.HacerseCargoHeladera;

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
import model.entities.Heladera.Heladera;

@Getter
@Setter
@Entity
@DiscriminatorValue("hacerseCargoHeladera")
public class HacerseCargoHeladera extends Contribucion {
  @OneToMany
  @JoinColumn(name = "ACargo_id", referencedColumnName = "id")
  private List<Heladera> heladeras;

  public HacerseCargoHeladera(Colaborador contribuidor, LocalDateTime fechaHora) {
    super(contribuidor, fechaHora);
    this.heladeras = new ArrayList<>();
  }

  public HacerseCargoHeladera() {
    this.heladeras = new ArrayList<>();
  }

  public void agregarHeladera(Heladera heladera) {
    this.heladeras.add(heladera);
  }

  @Override
  public Double calcularPuntos(Properties coeficientes) {
    double mesesActivosEnTotal = this.heladeras.stream().mapToDouble(Heladera::mesesActiva).sum();
    double coeficiente = Double.parseDouble(coeficientes.getProperty("HELADERAS"));
    return mesesActivosEnTotal * heladeras.size() * coeficiente;
  }
}