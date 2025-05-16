package model.entities.Contribucion.DonacionDinero;

import java.time.LocalDateTime;
import java.util.Properties;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Contribucion.Contribucion;

@Getter
@Setter
@Entity
@DiscriminatorValue("donacionDinero")
public class DonacionDinero extends Contribucion {
  @Column(name = "cantidadDinero")
  private int cantidadDinero;
  @Column(name = "frecuencia")
  private int frecuencia; // MODIFICAR

  public DonacionDinero(Colaborador contribuidor, LocalDateTime fechaHora, int cantidadDinero, int frecuencia) {
    super(contribuidor, fechaHora);
    this.cantidadDinero = cantidadDinero;
    this.frecuencia = frecuencia;
  }

  public DonacionDinero(Colaborador colaborador, LocalDateTime localDateTime, int cantidad) {
    super(colaborador, localDateTime);
    this.cantidadDinero = cantidad;
  }

  public DonacionDinero(LocalDateTime localDateTime, int cantidad) {
    super( localDateTime);
    this.cantidadDinero = cantidad;
  }

  public DonacionDinero() {
  }

  @Override
  public Double calcularPuntos(Properties coeficientes) {
    double coeficiente = Double.parseDouble(coeficientes.getProperty("PESOS"));
    return coeficiente * cantidadDinero;
  }
}

