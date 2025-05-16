package model.entities.Contribucion.RegistroPersonaEnSituacionVulnerable;

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
import model.entities.PersonaVulnerable.PersonaVulnerable;

@Getter
@Setter
@Entity
@DiscriminatorValue("registrarPersonaVulnerable")
public class RegistroPersonaVulnerable extends Contribucion {
  @OneToMany
  @JoinColumn(name = "personaVulnerable_id", referencedColumnName = "id")
  private List<PersonaVulnerable> personasVulnerables = new ArrayList<>();

  public RegistroPersonaVulnerable(Colaborador contribuidor, LocalDateTime fechaHora, PersonaVulnerable personaVulnerable) {
    super(contribuidor, fechaHora);
    this.personasVulnerables = new ArrayList<>();
  }

  public RegistroPersonaVulnerable(LocalDateTime fechaHora) {
    super(fechaHora);
  }

  public RegistroPersonaVulnerable() {
    this.personasVulnerables = new ArrayList<>();
  }

  public void agregarVulnerable(PersonaVulnerable personaVulnerable) {
    this.personasVulnerables.add(personaVulnerable);
  }

  @Override
  public Double calcularPuntos(Properties coeficientes) {
    double coeficiente = Double.parseDouble(coeficientes.getProperty("TARJETAS"));
    return personasVulnerables.size() * coeficiente;
  }
}