package model.dtos;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import model.entities.Vianda.Vianda;

@Getter
@Setter
public class ViandaDTO {
  private Long id;
  private String comidaNombre;
  private LocalDate fechaCaducidad;
  private String heladeraNombre;
  private Long heladeraID;
  private Long colaboradorID;

  public ViandaDTO(Vianda vianda) {
    this.id = vianda.getId();
    this.comidaNombre = vianda.getComida().getNombre();
    this.fechaCaducidad = vianda.getFechaCaducidad();
    this.heladeraNombre = vianda.getHeladera().getPuntoGeografico().getDireccion().show();
    this.heladeraID = vianda.getHeladera().getId();
    this.colaboradorID = vianda.getColaborador().getId();
  }
}
