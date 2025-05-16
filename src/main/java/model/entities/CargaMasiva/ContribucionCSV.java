package model.entities.CargaMasiva;

import java.time.LocalDate;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.entities.Colaborador.FormaDeContribucion;

@Getter
@Setter
@AllArgsConstructor
public class ContribucionCSV {
  private LocalDate fechaDeContribucion;
  private FormaDeContribucion formaDeContribucion;
  private int cantidad;
}
