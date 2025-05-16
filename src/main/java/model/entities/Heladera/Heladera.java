package model.entities.Heladera;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.entities.Colaborador.Persistente;
import model.entities.Contribucion.Contribucion;
import model.entities.Direccion.Calle;
import model.entities.Direccion.Localidad;
import model.entities.Heladera.SolicitudSobreHeladera.SolicitudSobreHeladera;
import model.entities.Incidente.Incidente;
import model.entities.PuntoGeografico.PuntoGeografico;
import model.entities.Suscripcion.Suscripcion;
import model.entities.Tarjeta.Tarjeta;

@Getter
@Setter
@Entity
@Table(name = "heladera")
@Builder
@AllArgsConstructor
@ToString
public class Heladera extends Persistente {
  @OneToOne
  @JoinColumn(name = "puntoGeografico_id", referencedColumnName = "id")
  private PuntoGeografico puntoGeografico;

  @Column(name = "capacidadViandas", nullable = false)
  private Integer capacidadViandas;

  @Column(name = "fechaDeInicio", nullable = false)
  private LocalDate fechaDeInicio;

  @Column(name = "estado_heladera", nullable = false)
  private EstadoHeladera estadoHeladera;

  @ManyToOne
  @JoinColumn(name = "modelo")
  private Modelo modelo;

  @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
  private List<RegistroHeladera> registroActividad = new ArrayList<>();

  @Column(name = "cantViandas")
  private Integer cantViandas = 0;

  @OneToMany
  @JoinColumn(name = "heladera_id")
  private List<Suscripcion> suscripciones;

  @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
  private List<SolicitudSobreHeladera> solicitudesDeAperturas;

  @Column(name = "temperaturaActual")
  private Float temperaturaActual;

  @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
  private List<RegistroViandasHeladera> registrosViandasHeladera;

  @OneToMany(mappedBy = "heladera", cascade = CascadeType.ALL)
  private List<Incidente> incidentes;

  private String topic;

  public Heladera() {
  }

  public Heladera(PuntoGeografico puntoGeografico, Integer capacidadViandas, LocalDate fechaDeInicio, EstadoHeladera estadoHeladera) {
    this.puntoGeografico = puntoGeografico;
    this.capacidadViandas = capacidadViandas;
    this.fechaDeInicio = fechaDeInicio;
    this.estadoHeladera = estadoHeladera;
    this.cantViandas = 0;
    this.registroActividad = new ArrayList<>();
    this.suscripciones = new ArrayList<>();
    this.solicitudesDeAperturas = new ArrayList<>();
    this.registrosViandasHeladera = new ArrayList<>();
    this.incidentes = new ArrayList<>();
    this.topic = UUID.randomUUID().toString();
  }

  public void agregarRegistroHeladera(RegistroHeladera registroHeladera) {
    if (this.registroActividad == null) {
      this.registroActividad = new ArrayList<>();
    }
    this.registroActividad.add(registroHeladera);
    registroHeladera.setHeladera(this); // Relación bidireccional
  }

  public String getTopic() {
    return getId().toString();
  }

  public void agregarSuscripcion(Suscripcion suscripcion) {
    this.suscripciones.add(suscripcion);
  }

  public Integer mesesActiva() {
    int cantMeses = 0;
    LocalDate fechaTomada = fechaDeInicio;

    if (registroActividad == null) {
      return 0;
    }

    for (RegistroHeladera registro : registroActividad) {
      if (registro.getEstado() == EstadoHeladera.ACTIVA) {
        fechaTomada = registro.getFecha();
      } else {
        cantMeses += this.calcularMesesDadoPeriodo(fechaTomada, registro.getFecha());
      }
    }

    // En caso de que la heladera quede activa
    if (estadoHeladera == EstadoHeladera.ACTIVA) {
      cantMeses += this.calcularMesesDadoPeriodo(fechaTomada, LocalDate.now());
    }

    return cantMeses;
  }

  public int calcularMesesDadoPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
    int cantMeses = 0;
    Period periodo = Period.between(fechaInicial, fechaFinal);
    cantMeses += periodo.getYears() * 12 + periodo.getMonths();
    return cantMeses;
  }

  public Calle calle() {
    return puntoGeografico.calle();
  }

  public Integer altura() {
    return puntoGeografico.altura();
  }

  public Localidad localidad() {
    return puntoGeografico.localidad();
  }

  public void agregarViandas(Integer cantidad) throws IOException {
    this.cantViandas += cantidad;
    registrosViandasHeladera.add(new RegistroViandasHeladera(LocalDate.now(), cantidad, this));
    cumpleCondicion();
  }

  public void agregarSolicitud(SolicitudSobreHeladera soli) {
    solicitudesDeAperturas.add(soli);
  }

  public void sacarViandas(Integer cantidad) throws IOException {
    this.cantViandas -= cantidad;
    if (this.cantViandas < 0) {
      this.cantViandas = 0;
    }

    registrosViandasHeladera.add(new RegistroViandasHeladera(LocalDate.now(), -cantidad, this));
    cumpleCondicion();
  }

  public boolean estaActiva() {
    return estadoHeladera == EstadoHeladera.ACTIVA;
  }

  public void setEstadoHeladera(EstadoHeladera estadoHeladeraNuevo) throws IOException {
    this.estadoHeladera = estadoHeladeraNuevo;
    cumpleCondicion();
  }

  public void setEstadoHeladera2(EstadoHeladera estadoHeladeraNuevo) {
    this.estadoHeladera = estadoHeladeraNuevo;
  }

  public void cumpleCondicion() throws IOException {
    if (suscripciones == null || suscripciones.isEmpty()) {
      System.out.println("No hay suscripciones para verificar.");
      return;
    }

    for (Suscripcion suscripcion : suscripciones) {
      if (suscripcion.cumpleCondicion()) {
        System.out.println("Llamando notificador para suscripciones");
        //suscripcion.llamarNotificador();
      }
    }
  }

  public void agregarIncidente(Incidente incidente) {
    incidentes.add(incidente);
  }

  public void quitarIncidente(Incidente incidente) {
    incidentes.remove(incidente);
  }

  public Boolean tienePermisos(String idTarjetaColaborador) throws IOException {
    boolean tienePermisos = false;
    for (SolicitudSobreHeladera solicitud : solicitudesDeAperturas) {
      if (Objects.equals(solicitud.getTarjetaColaborador().getCodigo(), idTarjetaColaborador) &&
          solicitud.getFechaYHora().getDayOfYear() == LocalDate.now().getDayOfYear() &&
          solicitud.getFechaYHora().getHour() >= LocalTime.now().getHour() - 3) {
        tienePermisos = true;
        agregarViandas(1);
        break;
      }
    }
    return tienePermisos;
  }

  public void registrarSolicitud(Contribucion accion, Tarjeta tarjetaColaborador) {
    solicitudesDeAperturas.add(new SolicitudSobreHeladera(accion, tarjetaColaborador, LocalDateTime.now()));
  }

  public Boolean temperaturaPermitida(Float nuevaTemperatura) {
    temperaturaActual = nuevaTemperatura;
    return temperaturaActual <= modelo.getTempMaxima() && temperaturaActual >= modelo.getTempMinima();
  }

  public void agregarRegistroViandas(RegistroViandasHeladera registroViandasHeladera) {
    registrosViandasHeladera.add(registroViandasHeladera);
  }

  public void evaluarSiTieneIndidentesNoResueltos() {
    if (this.incidentes.stream().allMatch(i -> i.getEstaResuelto())) {
      this.estadoHeladera = EstadoHeladera.ACTIVA;
    }
  }
}
