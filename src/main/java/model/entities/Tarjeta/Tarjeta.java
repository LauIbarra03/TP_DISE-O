package model.entities.Tarjeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.PersonaVulnerable.PersonaVulnerable;

@Getter
@Entity
@Table(name = "tarjeta")
public class Tarjeta extends Persistente {
  @Column(name = "codigo")
  private String codigo;
  @Setter
  @OneToMany
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
  private List<UsoTarjeta> usos;

  public Tarjeta(PersonaVulnerable persona) throws IOException {
    this.codigo = UUID.randomUUID().toString();
    this.usos = new ArrayList<>();
  }

  public Tarjeta() {
    this.codigo = UUID.randomUUID().toString();
    this.usos = new ArrayList<>();
  }
}