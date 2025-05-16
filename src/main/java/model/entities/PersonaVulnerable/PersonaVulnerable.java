package model.entities.PersonaVulnerable;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Persistente;
import model.entities.Direccion.Direccion;
import model.entities.Heladera.Heladera;
import model.entities.Tarjeta.Tarjeta;
import model.entities.Tarjeta.UsoTarjeta;
import model.entities.Utils.TipoDocumento;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "personaVulnerable")
public class PersonaVulnerable extends Persistente {
  @Transient
  private static final String USOS_DIARIOS_PATH = "src/main/java/ar/edu/utn/frba/dds/Utils/usosDiarios.properties";
  @Transient
  private static Properties usosDiariosConfig;
  @Column(name = "nombre")
  private String nombre;
  @Column(name = "fechaDeNacimiento")
  private LocalDate fechaDeNacimiento;
  @Column(name = "fechaRegistro")
  private LocalDateTime fechaRegistro;
  @ManyToOne
  @JoinColumn(name = "direccion_id", referencedColumnName = "id")
  private Direccion direccion;
  @Enumerated(EnumType.STRING)
  private TipoDocumento tipoDocumento;
  @Column(name = "numeroDocumento")
  private String numeroDocumento;
  @OneToMany
  @JoinColumn(name = "padre_id", referencedColumnName = "id")
  private List<PersonaVulnerable> menoresACargo;
  @OneToOne
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
  private Tarjeta tarjeta;

  public PersonaVulnerable(String nombre, LocalDate fechaDeNacimiento, Direccion direccion,
                           TipoDocumento tipoDocumento, String numeroDocumento, Tarjeta tarjeta) throws IOException {
    this.nombre = nombre;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.fechaRegistro = LocalDateTime.now();
    this.direccion = direccion;
    this.tipoDocumento = tipoDocumento;
    this.numeroDocumento = numeroDocumento;
    this.menoresACargo = new ArrayList<>();
    this.tarjeta = tarjeta;
    cargarUsosDiarios();
  }

  public PersonaVulnerable() {

  }


  public void agregarMenorACargo(PersonaVulnerable menor) {
    if (!menor.esMayor()) {
      menoresACargo.add(menor);
    }
  }

  public boolean esMayor() {
    return LocalDate.now().minusYears(18).isAfter(fechaDeNacimiento);
  }

  public int cantidadDeMenoresACargo() {
    if (menoresACargo == null) return 0;
    menoresACargo.removeIf(menor -> menor.esMayor());
    return menoresACargo.size();
  }

  public boolean sitaucionDeCalle() {
    return direccion == null;
  }

  public void cargarUsosDiarios() throws IOException {
    usosDiariosConfig = new Properties();
    FileInputStream ip = new FileInputStream(USOS_DIARIOS_PATH);
    usosDiariosConfig.load(ip);
  }

  public int cantidadDeUsosPorDia() {
    int usosBase = Integer.parseInt(usosDiariosConfig.getProperty("USOS_BASE"));
    int usosPorHijo = Integer.parseInt(usosDiariosConfig.getProperty("USOS_POR_HIJO"));
    return 4 + this.cantidadDeMenoresACargo() * 2;
  }

  public boolean usarTarjeta(Heladera heladera) {
    LocalDateTime now = LocalDateTime.now();
    int usosHoy = (int) tarjeta.getUsos().stream()
        .filter(uso -> uso.getFechaHora().toLocalDate().equals(now.toLocalDate()))
        .count();

    int usosMax = cantidadDeUsosPorDia();

    if (usosHoy < usosMax) {
      tarjeta.getUsos().add(new UsoTarjeta(now, heladera, this));
      return true;
    } else {
      return false;
    }
  }
}