package model.entities.Incidente;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.Persistente;
import model.entities.Heladera.Heladera;
import model.entities.Tecnico.RegistroVisitaTecnica;

@Getter
@Setter
@Builder
@Entity
@Table(name = "incidente")
public class Incidente extends Persistente {

  @Column(name = "fechaYHora", columnDefinition = "DATETIME")
  private LocalDateTime fechaYHora;

  @Enumerated(EnumType.STRING)
  private TipoIncidente tipoIncidente;

  @Enumerated(EnumType.STRING)
  private TipoAlerta tipoAlerta;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "heladera_id", referencedColumnName = "id")
  private Heladera heladera;

  @ManyToOne
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private Colaborador colaborador;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Column(name = "foto")
  private String foto;

  @Column(name = "estaResuelto")
  private Boolean estaResuelto;

  @OneToMany(mappedBy = "incidente")
  private List<RegistroVisitaTecnica> visitasTecnicas;

  public Incidente() {
  }

  // agrego un constructor completo porque sino rompe
  public Incidente(LocalDateTime fechaYHora, TipoIncidente tipoIncidente, TipoAlerta tipoAlerta,
                   Heladera heladera, Colaborador colaborador, String descripcion,
                   String foto, Boolean estaResuelto, List<RegistroVisitaTecnica> visitasTecnicas) {
    this.fechaYHora = fechaYHora;
    this.tipoIncidente = tipoIncidente;
    this.tipoAlerta = tipoAlerta;
    this.heladera = heladera;
    this.colaborador = colaborador;
    this.descripcion = descripcion;
    this.foto = foto;
    this.estaResuelto = estaResuelto;
    this.visitasTecnicas = visitasTecnicas;
  }

  public static Incidente of(LocalDateTime fechaYHora, TipoIncidente tipoIncidente, TipoAlerta tipoAlerta, Heladera heladera) {
    return Incidente
        .builder()
        .fechaYHora(fechaYHora)
        .tipoIncidente(tipoIncidente)
        .tipoAlerta(tipoAlerta)
        .estaResuelto(false)
        .heladera(heladera)
        .build();
  }

  public static Incidente of(LocalDateTime fechaYHora, TipoIncidente tipoIncidente, Heladera heladera,
                             Colaborador colaborador, String descripcion, String foto) {
    return Incidente
        .builder()
        .fechaYHora(fechaYHora)
        .tipoIncidente(tipoIncidente)
        .heladera(heladera)
        .colaborador(colaborador)
        .descripcion(descripcion)
        .estaResuelto(false)
        .foto(foto)
        .build();
  }

  public void agregarVisitaTecnica(RegistroVisitaTecnica visitaTecnica) {
    visitasTecnicas.add(visitaTecnica);
  }

}