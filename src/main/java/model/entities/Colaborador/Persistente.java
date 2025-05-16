package model.entities.Colaborador;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Persistente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Setter
  @Column(name = "activo")
  private Boolean activo = true;

  @Column(name = "fechaAlta", columnDefinition = "DATE")
  private LocalDate fechaAlta;

  public Persistente() {
    this.fechaAlta = LocalDate.now();
  }
}
