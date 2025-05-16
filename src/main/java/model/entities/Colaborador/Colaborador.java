package model.entities.Colaborador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import model.entities.CalculadoraDePuntos.CalculadoraDePuntos;
import model.entities.Contribucion.Contribucion;
import model.entities.Direccion.Direccion;
import model.entities.Direccion.Localidad;
import model.entities.Heladera.Heladera;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.MedioDeContacto.TipoContacto;
import model.entities.Producto.Producto;
import model.entities.Producto.Rubro;
import model.entities.Suscripcion.Suscripcion;
import model.entities.Tarjeta.Tarjeta;
import model.entities.Utils.TipoDocumento;

@Setter
@Getter
@Entity
@Table(name = "colaborador")
@Builder
@AllArgsConstructor
public class Colaborador extends Persistente {
  @Column(name = "nombre")
  private String nombre;
  @Column(name = "apellido")
  private String apellido;
  @Column(name = "fechaNacimiento")
  private LocalDate fechaNacimiento;
  @Column(name = "razonSocial")
  private String razonSocial;
  @Enumerated(EnumType.STRING)
  @Column(name = "tipoOrganizacion")
  private TipoOrganizacion tipoOrganizacion;
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "tipoPersona_id", referencedColumnName = "id")
  private TipoPersona tipoPersona;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private List<MedioDeContacto> contactos = new ArrayList<>();
  @Column(name = "puntosGastados")
  private Integer puntosGastados = 0;
  @Column(name = "puntosTotales")
  private Integer puntosTotales;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private List<Contribucion> contribucionesRealizadas = new ArrayList<>();
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "comprador_id", referencedColumnName = "id")
  private List<Producto> productosComprados = new ArrayList<>();
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "vendedor_id", referencedColumnName = "id")
  private List<Producto> productosPublicados = new ArrayList<>();
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "direccion_id", referencedColumnName = "id")
  private Direccion direccion;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
  private List<Suscripcion> suscripciones = new ArrayList<>();
  @OneToOne
  private Tarjeta tarjeta;
  @ElementCollection(targetClass = FormaDeContribucion.class)
  @Enumerated(EnumType.STRING)
  @Column(name = "forma_contribucion")
  private Set<FormaDeContribucion> formasDeContribuirSeleccionadas = new HashSet<>();
  @ManyToOne
  @JoinColumn(name = "rubro_id", referencedColumnName = "id")
  private Rubro rubro;

  //datos utiles para la carga masiva
  @Enumerated(EnumType.STRING)
  @Column(name = "tipoDoc")
  private TipoDocumento tipoDoc;
  @Column(name = "numeroDocumento")
  private String documento;



  public Colaborador(String nombre, String apellido, LocalDate fechaNacimiento, String razonSocial, TipoOrganizacion tipoOrganizacion, TipoPersona tipoPersona, List<MedioDeContacto> contactos) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.fechaNacimiento = fechaNacimiento;
    this.razonSocial = razonSocial;
    this.tipoOrganizacion = tipoOrganizacion;
    this.tipoPersona = tipoPersona;
    this.contactos = contactos;
  }
 // Para Carga Masiva
  public Colaborador(String nombre, String apellido, MedioDeContacto contacto, TipoDocumento tipoDocumento,String documento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.contactos.add(contacto);
    this.tipoDoc = tipoDocumento;
    this.documento = documento;
  }


  public Colaborador(String trim, String trim1, String trim2, String trim3, String trim4, String trim5, String trim6, int i) {
  } // lo creo porque rompe si no esta

  public Colaborador() {

  }


  public int getPuntosTotales() {
    return (int) new CalculadoraDePuntos().calcularPuntosContribuidor(this);
  }

  public void agregarContribucion(Contribucion contribucion) {
    if (this.contribucionesRealizadas == null) {
      this.contribucionesRealizadas = new ArrayList<>();
    }
    this.contribucionesRealizadas.add(contribucion);
  }

  public void agregarProducto(Producto producto) {
    this.productosComprados.add(producto);
    this.puntosGastados += producto.getCantidadDePuntos();
  }

  public Boolean puedeSuscribirseA(Heladera heladera) {
    return this.localidad() == heladera.localidad();
  }

  public void agregarSuscripcion(Suscripcion suscripcion) {
    this.suscripciones.add(suscripcion);
  }

  public Localidad localidad() {
    return direccion.getLocalidad();
  }

  public boolean esHumana() {
    return this.tipoPersona.getNombre().equals("Humana");
  }

  public boolean puedeDonarVianda() {
    return formasDeContribuirSeleccionadas.contains(FormaDeContribucion.DonacionVianda);
  }

  public boolean puedeDonarDinero() {
    return formasDeContribuirSeleccionadas.contains(FormaDeContribucion.DonacionDinero);
  }

  public boolean puedeDistribuirViandas() {
    return formasDeContribuirSeleccionadas.contains(FormaDeContribucion.DistribucionDeViandas);
  }

  public boolean puedeRegistrarPersonaVulnerable() {
    return formasDeContribuirSeleccionadas.contains(FormaDeContribucion.RegistroPersonaEnSituacionVulnerable);
  }

  public boolean puedeHacerseCargoHeladera() {
    return formasDeContribuirSeleccionadas.contains(FormaDeContribucion.HaceseCargoHeladera);
  }

  public boolean puedeRealizarOfertas() {
    return formasDeContribuirSeleccionadas.contains(FormaDeContribucion.RealizarOfertas);
  }

  public MedioDeContacto obtenerPrimerContacto() {
    if (!contactos.isEmpty()) {
      return contactos.get(0);
    }
    return null;
  }

  public String getContactoPorMail() {
    return contactos.stream()
        .filter(contacto -> contacto.getTipoContacto().equals(TipoContacto.CORREO))
        .map(MedioDeContacto::getContacto)
        .findFirst()
        .orElse(null);
  }

  public String getContactoPorWpp() {
    return contactos.stream()
        .filter(contacto -> contacto.getTipoContacto().equals(TipoContacto.WHATSAPP))
        .map(MedioDeContacto::getContacto)
        .findFirst()
        .orElse(null);
  }

  public String getContactoPorTelegram() {
    return contactos.stream()
        .filter(contacto -> contacto.getTipoContacto().equals(TipoContacto.TELEGRAM))
        .map(MedioDeContacto::getContacto)
        .findFirst()
        .orElse(null);
  }

  public String getContactoPorTelefono() {
    return contactos.stream()
        .filter(contacto -> contacto.getTipoContacto().equals(TipoContacto.TELEFONO))
        .map(MedioDeContacto::getContacto)
        .findFirst()
        .orElse(null);
  }
}