package model.entities.CargaMasiva;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;

@Getter
@Setter
@AllArgsConstructor
public class ColaboradorContribucionCSV {
  private Colaborador colaborador;
  private ContribucionCSV contribucionCSV;
}
