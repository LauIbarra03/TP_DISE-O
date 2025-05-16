package ar.edu.utn.frba.dds.PuntosDeRecocimiento;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.entities.CalculadoraDePuntos.CalculadoraDePuntos;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.TipoOrganizacion;
import model.entities.Colaborador.TipoPersona;
import model.entities.Contribucion.DistribucionVianda.DistribucionViandas;
import model.entities.Contribucion.DonacionDinero.DonacionDinero;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;
import model.entities.Contribucion.HacerseCargoHeladera.HacerseCargoHeladera;
import model.entities.Contribucion.RegistroPersonaEnSituacionVulnerable.RegistroPersonaVulnerable;
import model.entities.Direccion.Direccion;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Heladera.RegistroHeladera;
import model.entities.PersonaVulnerable.PersonaVulnerable;
import model.entities.Producto.Producto;
import model.entities.Producto.Rubro;
import model.entities.PuntoGeografico.PuntoGeografico;
import model.entities.Tarjeta.Tarjeta;
import model.entities.Vianda.Comida;
import model.entities.Vianda.EstadoVianda;
import model.entities.Vianda.Vianda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PuntosTest {
  private Vianda vianda1, vianda2, vianda3;
  private Tarjeta tarjeta1, tarjeta2, tarjeta3;
  private PersonaVulnerable vulnerable;
  private Colaborador colaboradorHumana1, colaboradorHumana2, colaboradorHumana3, colaboradorJurdica1, colaboradorJurdica2, colaboradorJurdica3;
  private Heladera heladera1, heladera2, heladera3;
  private Direccion direccion;
  private LocalDate fecha1, fecha2, fecha3;
  private LocalDateTime fechahora;
  private PuntoGeografico punto;
  private RegistroHeladera registroHeladera1, registroHeladera2, registroHeladera3;
  private HacerseCargoHeladera hacerseCargoHeladera1, hacerseCargoHeladera2, hacerseCargoHeladera3;
  private DistribucionViandas distribucionViandas1, distribucionViandas2, distribucionViandas3;
  private DonacionDinero donacionDinero1, donacionDinero2, donacionDinero3;
  private DonacionVianda donacionVianda1, donacionVianda2, donacionVianda3;
  private RegistroPersonaVulnerable registroPersonaVulnerable1, registroPersonaVulnerable2, registroPersonaVulnerable3;
  private CalculadoraDePuntos calculadoraDePuntos;
  private Producto producto1, producto2, producto3, producto4, producto5;


  @BeforeEach
  public void setUp() throws IOException {

    direccion = new Direccion();
    fechahora = LocalDateTime.of(2024, 5, 26, 14, 30);

    /** HELADERA 1 **/
    punto = new PuntoGeografico(2f, 1f, direccion, "f");
    fecha1 = LocalDate.of(2023, 4, 20);
    heladera1 = new Heladera(punto, 5, fecha1, EstadoHeladera.ACTIVA);
    registroHeladera1 = new RegistroHeladera(LocalDate.of(2023, 5, 20), EstadoHeladera.NO_ACTIVA);
    registroHeladera2 = new RegistroHeladera(LocalDate.of(2023, 7, 20), EstadoHeladera.ACTIVA);
    heladera1.agregarRegistroHeladera(registroHeladera1);
    heladera1.agregarRegistroHeladera(registroHeladera2);

    /** HELADERA 2 **/
    fecha2 = LocalDate.of(2023, 4, 20);
    heladera2 = new Heladera(punto, 12, fecha2, EstadoHeladera.ACTIVA);
    registroHeladera3 = new RegistroHeladera(LocalDate.of(2023, 10, 20), EstadoHeladera.NO_ACTIVA);
    heladera2.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
    heladera2.agregarRegistroHeladera(registroHeladera3);

    /** HELADERA 3 **/
    fecha3 = LocalDate.of(2023, 1, 20);
    heladera3 = new Heladera(punto, 12, fecha3, EstadoHeladera.ACTIVA);

    /** VIANDAS **/
    vianda1 = new Vianda(new Comida("Milanesa"), fecha1, fecha2, colaboradorHumana1, heladera1, EstadoVianda.Entregada);
    vianda2 = new Vianda(new Comida("Fideos"), fecha1, fecha2, colaboradorHumana1, heladera1, EstadoVianda.Entregada);
    vianda3 = new Vianda(new Comida("Arroz"), fecha1, fecha2, colaboradorHumana1, heladera1, EstadoVianda.Entregada);

    /** LISTAS DE VIANDAS **/
    List<Vianda> viandas1 = new ArrayList<>();
    viandas1.add(vianda1);
    viandas1.add(vianda2);
    viandas1.add(vianda3);

    List<Vianda> viandas2 = new ArrayList<>();
    viandas2.add(vianda1);
    viandas2.add(vianda2);

    List<Vianda> viandas3 = new ArrayList<>();
    viandas3.add(vianda3);

    /** TARJETA_VULNERABLE **/
    tarjeta1 = new Tarjeta(vulnerable);
    tarjeta2 = new Tarjeta(vulnerable);
    tarjeta3 = new Tarjeta(vulnerable);

    /** PERSONA VULNERABLE **/
    vulnerable = new PersonaVulnerable();

    /** PRODUCTOS **/
    producto1 = new Producto("Heladera", new Rubro("Electrodomestico"), 1000, "a");
    producto2 = new Producto("Monitor", new Rubro("Electronica"), 250, "a");
    producto3 = new Producto("Set de ollas", new Rubro("Gastronomia"), 500, "a");
    producto4 = new Producto("Saga completa de El Archivo de las Tormentas", new Rubro("Libros"), 250, "a");
    producto5 = new Producto("Coleccion de Angelica Gorodischer", new Rubro("Libros"), 250, "a");

    /** CALCULADORA **/
    calculadoraDePuntos = new CalculadoraDePuntos();

    /** CONTRIBUCION - HACERSE_CARGO_HELADERA **/
    hacerseCargoHeladera1 = new HacerseCargoHeladera(colaboradorHumana1, fechahora);
    hacerseCargoHeladera1.agregarHeladera(heladera1);
    hacerseCargoHeladera1.agregarHeladera(heladera2);
    hacerseCargoHeladera1.agregarHeladera(heladera3);

    hacerseCargoHeladera2 = new HacerseCargoHeladera(colaboradorHumana1, fechahora);
    hacerseCargoHeladera2.agregarHeladera(heladera2);
    hacerseCargoHeladera2.agregarHeladera(heladera1);

    hacerseCargoHeladera3 = new HacerseCargoHeladera(colaboradorHumana1, fechahora);
    hacerseCargoHeladera3.agregarHeladera(heladera3);

    /** CONTRIBUCION - DISTRIBUCION_VIANDAS**/
    distribucionViandas1 = new DistribucionViandas(fechahora, colaboradorHumana1, heladera1, heladera2, viandas1, "a");
    distribucionViandas2 = new DistribucionViandas(fechahora, colaboradorHumana1, heladera1, heladera2, viandas2, "a");
    distribucionViandas3 = new DistribucionViandas(fechahora, colaboradorHumana1, heladera1, heladera2, viandas3, "a");

    /** CONTRIBUCION - DONACION_DINERO **/
    donacionDinero1 = new DonacionDinero(colaboradorHumana1, fechahora, 500, 1);
    donacionDinero2 = new DonacionDinero(colaboradorHumana1, fechahora, 1000, 1);
    donacionDinero3 = new DonacionDinero(colaboradorHumana1, fechahora, 2000, 1);

    /** CONTRIBUCION - DONACION_VIANDA **/
    donacionVianda1 = new DonacionVianda(colaboradorHumana1, fechahora, viandas1);
    donacionVianda2 = new DonacionVianda(colaboradorHumana1, fechahora, viandas2);
    donacionVianda3 = new DonacionVianda(colaboradorHumana1, fechahora, viandas3);

    /** CONTRIBUCION - REPARTIR_TARJETAS **/
    registroPersonaVulnerable1 = new RegistroPersonaVulnerable(colaboradorHumana1, fechahora, vulnerable);


    registroPersonaVulnerable2 = new RegistroPersonaVulnerable(colaboradorHumana1, fechahora, vulnerable);


    registroPersonaVulnerable3 = new RegistroPersonaVulnerable(colaboradorHumana1, fechahora, vulnerable);

    /** PERSONA HUMANA 1**/
    colaboradorHumana1 = new Colaborador(
        "Pablo",
        "Alboran",
        LocalDate.of(2000, 1, 1),
        null,
        null,
        new TipoPersona("humana", new ArrayList<>()),
        null) {
    };
    colaboradorHumana1.agregarContribucion(donacionDinero1);
    colaboradorHumana1.agregarContribucion(donacionDinero3);
    colaboradorHumana1.agregarContribucion(donacionVianda3);
    colaboradorHumana1.agregarContribucion(distribucionViandas1);
    colaboradorHumana1.agregarContribucion(registroPersonaVulnerable1);

    colaboradorHumana1.agregarProducto(producto1);
    colaboradorHumana1.agregarProducto(producto2);
    colaboradorHumana1.agregarProducto(producto3);

    /** PERSONA HUMANA 2**/
    colaboradorHumana2 = new Colaborador(
        "Andres",
        "Calamaro",
        LocalDate.of(2000, 1, 1),
        null,
        null,
        new TipoPersona("humana", new ArrayList<>()),
        null) {
    };
    colaboradorHumana2.agregarContribucion(donacionDinero2);
    colaboradorHumana2.agregarContribucion(registroPersonaVulnerable2);
    colaboradorHumana2.agregarContribucion(registroPersonaVulnerable3);
    colaboradorHumana2.agregarContribucion(distribucionViandas2);
    colaboradorHumana2.agregarContribucion(distribucionViandas3);

    colaboradorHumana2.agregarProducto(producto4);
    colaboradorHumana2.agregarProducto(producto5);

    /** PERSONA HUMANA 3**/
    colaboradorHumana3 = new Colaborador(
        "Angelica",
        "Gorodischer",
        LocalDate.of(2000, 1, 1),
        null,
        null,
        new TipoPersona("humana", new ArrayList<>()),
        null) {
    };
    colaboradorHumana3.agregarContribucion(donacionDinero1);
    colaboradorHumana3.agregarContribucion(donacionDinero2);
    colaboradorHumana3.agregarContribucion(donacionDinero3);
    colaboradorHumana3.agregarContribucion(distribucionViandas3);
    colaboradorHumana3.agregarContribucion(distribucionViandas2);
    colaboradorHumana3.agregarContribucion(distribucionViandas1);
    colaboradorHumana3.agregarContribucion(registroPersonaVulnerable3);
    colaboradorHumana3.agregarContribucion(registroPersonaVulnerable2);
    colaboradorHumana3.agregarContribucion(registroPersonaVulnerable1);
    colaboradorHumana3.agregarContribucion(donacionVianda1);
    colaboradorHumana3.agregarContribucion(donacionVianda2);
    colaboradorHumana3.agregarContribucion(donacionVianda3);

    colaboradorHumana3.agregarProducto(producto1);
    colaboradorHumana3.agregarProducto(producto2);
    colaboradorHumana3.agregarProducto(producto3);
    colaboradorHumana3.agregarProducto(producto4);
    colaboradorHumana3.agregarProducto(producto5);

    /** PERSONA JURIDICA 1 **/
    colaboradorJurdica1 = new Colaborador(
        null,
        null,
        LocalDate.of(2000, 1, 1),
        "razonSocial",
        TipoOrganizacion.Empresa,
        new TipoPersona("juridica", new ArrayList<>()),
        null) {
    };
    colaboradorJurdica1.agregarContribucion(hacerseCargoHeladera1);
    colaboradorJurdica1.agregarContribucion(hacerseCargoHeladera2);
    colaboradorJurdica1.agregarContribucion(donacionDinero1);

    /** PERSONA JURIDICA 2 **/
    colaboradorJurdica2 = new Colaborador(
        null,
        null,
        LocalDate.of(2000, 1, 1),
        "razonSocial2",
        TipoOrganizacion.ONG,
        new TipoPersona("juridica", new ArrayList<>()),
        null) {
    };
    colaboradorJurdica2.agregarContribucion(hacerseCargoHeladera3);
    colaboradorJurdica2.agregarContribucion(donacionDinero2);
    colaboradorJurdica2.agregarContribucion(donacionDinero3);

    /** PERSONA JURIDICA 3 **/
    colaboradorJurdica3 = new Colaborador(
        null,
        null,
        LocalDate.of(2000, 1, 1),
        "razonSocial3",
        TipoOrganizacion.Gubernamental,
        new TipoPersona("juridica", new ArrayList<>()),
        null) {
    };
    colaboradorJurdica3.agregarContribucion(hacerseCargoHeladera1);
    colaboradorJurdica3.agregarContribucion(hacerseCargoHeladera2);
    colaboradorJurdica3.agregarContribucion(hacerseCargoHeladera3);
    colaboradorJurdica3.agregarContribucion(donacionDinero1);
    colaboradorJurdica3.agregarContribucion(donacionDinero2);
    colaboradorJurdica3.agregarContribucion(donacionDinero3);


  }

  @Test
  public void testMesesActivaHeladera() throws IOException {
    double mesesHeladera1 = heladera1.mesesActiva();
    double mesesHeladera2 = heladera2.mesesActiva();
    double mesesHeladera3 = heladera3.mesesActiva();

    /* EN UN FUTURO VAN A ROMPER LOS TESTS ESTOS, PQ EL VALOR ESPERADO VA A CAMBIAR
     * HOY ES 2024/06/26, EL DIA Q HICE LOS TESTS, SOLO HAY QUE SUMAR EL VALOR DE LOS MESES Q PASARON */

    assertEquals(12, mesesHeladera1, "Meses heladera 1 (con un cambio entre registros)");
    assertEquals(6, mesesHeladera2, "Meses heladera 2 (no se vuelve a activar)");
    assertEquals(17, mesesHeladera3, "Meses heladera 3 (siempre activa)");

  }

  @Test
  public void testPuntosHeladera() throws IOException {
    double valor1 = hacerseCargoHeladera1.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor2 = hacerseCargoHeladera2.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor3 = hacerseCargoHeladera3.calcularPuntos(calculadoraDePuntos.getCoeficientes());

    assertEquals(525, valor1, "Puntos acumulados por hacerse cargo de heladeras 1");
    assertEquals(180, valor2, "Puntos acumulados por hacerse cargo de heladeras 2");
    assertEquals(85, valor3, "Puntos acumulados por hacerse cargo de heladeras 3");
  }

  @Test
  public void testPuntosDistribucionViandas() throws IOException {
    double valor1 = distribucionViandas1.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor2 = distribucionViandas2.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor3 = distribucionViandas3.calcularPuntos(calculadoraDePuntos.getCoeficientes());

    assertEquals(3, valor1, "Puntos acumulados por distribuir 3 viandas");
    assertEquals(2, valor2, "Puntos acumulados por distribuir 2 viandas");
    assertEquals(1, valor3, "Puntos acumulados por distribuir 1 viandas");
  }

  @Test
  public void testDonacionDinero() throws IOException {
    double valor1 = donacionDinero1.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor2 = donacionDinero2.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor3 = donacionDinero3.calcularPuntos(calculadoraDePuntos.getCoeficientes());

    assertEquals(250, valor1, "Puntos por donar: 500 dinero");
    assertEquals(500, valor2, "Puntos por donar: 1000 dinero");
    assertEquals(1000, valor3, "Puntos por donar: 2000 dinero");
  }

  @Test
  public void testDonacionVianda() throws IOException {
    double valor1 = donacionVianda1.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor2 = donacionVianda2.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor3 = donacionVianda3.calcularPuntos(calculadoraDePuntos.getCoeficientes());

    assertEquals(4.5, valor1, "Puntos por donar 3 vianda");
    assertEquals(3, valor2, "Puntos por donar 2 viandas");
    assertEquals(1.5, valor3, "Puntos por donar 1 viandas ");
  }

  @Test
  public void testRepartirTarjetas() throws IOException {
    double valor1 = registroPersonaVulnerable1.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor2 = registroPersonaVulnerable2.calcularPuntos(calculadoraDePuntos.getCoeficientes());
    double valor3 = registroPersonaVulnerable3.calcularPuntos(calculadoraDePuntos.getCoeficientes());

    assertEquals(2, valor1, "Puntos por repartir 1 tarjeta");
    assertEquals(4, valor2, "Puntos por repartir 2 tarjeta");
    assertEquals(6, valor3, "Puntos por repartir 3 tarjeta");
  }

  @Test
  public void testCalcularPuntosDeReconocimiento() throws IOException {
    double valorPersonaHumana1 = calculadoraDePuntos.calcularPuntosContribuidor(colaboradorHumana1);
    double valorPersonaHumana2 = calculadoraDePuntos.calcularPuntosContribuidor(colaboradorHumana2);
    double valorPersonaHumana3 = calculadoraDePuntos.calcularPuntosContribuidor(colaboradorHumana3);
    double valorPersonaJuridica1 = calculadoraDePuntos.calcularPuntosContribuidor(colaboradorJurdica1);
    double valorPersonaJuridica2 = calculadoraDePuntos.calcularPuntosContribuidor(colaboradorJurdica2);
    double valorPersonaJuridica3 = calculadoraDePuntos.calcularPuntosContribuidor(colaboradorJurdica3);


    assertEquals(1256.5, valorPersonaHumana1, "Total de puntos de reconocimientos GANADOS para persona humana 1");
    assertEquals(513, valorPersonaHumana2, "Total de puntos de reconocimientos GANADOS para persona humana 2");
    assertEquals(1777, valorPersonaHumana3, "Total de puntos de reconocimientos GANADOS para persona humana 3");
    assertEquals(955, valorPersonaJuridica1, "Total de puntos de reconocimientos GANADOS para persona juridica 1");
    assertEquals(1585, valorPersonaJuridica2, "Total de puntos de reconocimientos GANADOS para persona juridica 2");
    assertEquals(2540, valorPersonaJuridica3, "Total de puntos de reconocimientos GANADOS para persona juridica 3");
  }

  @Test
  public void testCalcularPuntosDeReconocimientoGastados() throws IOException {
    double valorPersonaHumana1 = calculadoraDePuntos.calcularPuntosGastados(colaboradorHumana1);
    double valorPersonaHumana2 = calculadoraDePuntos.calcularPuntosGastados(colaboradorHumana2);
    double valorPersonaHumana3 = calculadoraDePuntos.calcularPuntosGastados(colaboradorHumana3);

    assertEquals(1750, valorPersonaHumana1, "Total de puntos de reconocimientos GASTADOS para persona humana 1");
    assertEquals(500, valorPersonaHumana2, "Total de puntos de reconocimientos GASTADOS para persona humana 2");
    assertEquals(2250, valorPersonaHumana3, "Total de puntos de reconocimientos GASTADOS para persona humana 3");

  }
}




