package ar.edu.utn.frba.dds.Suscripciones;


import java.io.IOException;
import java.time.LocalDate;
import model.entities.Direccion.Direccion;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.MedioDeContacto.TipoContacto;
import model.entities.Notificador.Notificador;
import model.entities.PuntoGeografico.PuntoGeografico;
import model.entities.Suscripcion.DesperfectoHeladera.DesperfectoHeladera;
import model.entities.Suscripcion.MuchasViandas.MuchasViandas;
import model.entities.Suscripcion.PocasViandas.PocasViandas;
import model.entities.Suscripcion.Suscripcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuscripcionesTest {
  private Notificador notificador;
  private Heladera heladera1, heladera2;
  private Suscripcion suscripcionMuchasViandasCorreo, suscripcionMuchasViandasTelegram, suscripcionMuchasViandasWhatsApp,
      suscripcionPocasViandasTelegram, suscripcionPocasViandasCorreo, suscripcionPocasViandasWhatsApp,
      suscripcionDesperfectoHeladeraTelegram1, suscripcionDesperfectoHeladeraTelegram2, suscripcionDesperfectoHeladeraCorreo, suscripcionDesperfectoHeladeraWhatsApp;

  @BeforeEach
  public void setUp() {
    notificador = Notificador.getInstance();

    /** HELADERAS **/
    heladera1 =
        new Heladera(new PuntoGeografico(1f,
            1f,
            new Direccion(),
            "a"),
            22,
            LocalDate.now(),
            EstadoHeladera.ACTIVA);
    heladera2 =
        new Heladera();

    /** TIPOS DE SUSCRIPCIONES **/
    MuchasViandas muchasViandas = new MuchasViandas(21);
    PocasViandas pocasViandas = new PocasViandas(3);
    DesperfectoHeladera desperfectoHeladera = new DesperfectoHeladera();

    /** SUSCRIPCION MUCHAS VIANDAS **/
    suscripcionMuchasViandasCorreo = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.CORREO, "laibarra@frba.utn.edu.ar"), muchasViandas);
    suscripcionMuchasViandasTelegram = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.TELEGRAM, "5827432205"), muchasViandas);
    suscripcionMuchasViandasWhatsApp = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.WHATSAPP, "+5491140801782"), muchasViandas);

    /** SUSCRIPCION POCAS VIANDAS **/
    suscripcionPocasViandasTelegram = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.TELEGRAM, "5827432205"), pocasViandas);
    suscripcionPocasViandasCorreo = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.CORREO, "laibarra@frba.utn.edu.ar"), pocasViandas);
    suscripcionPocasViandasWhatsApp = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.WHATSAPP, "+5491140801782"), pocasViandas);

    /** SUSCRIPCION DESPERFECTO HELADERA **/
    suscripcionDesperfectoHeladeraTelegram1 = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.TELEGRAM, "5827432205"), desperfectoHeladera);
    suscripcionDesperfectoHeladeraTelegram2 = new Suscripcion(heladera2, new MedioDeContacto(TipoContacto.TELEGRAM, "5827432205"), desperfectoHeladera);
    suscripcionDesperfectoHeladeraCorreo = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.CORREO, "laibarra@frba.utn.edu.ar"), desperfectoHeladera);
    suscripcionDesperfectoHeladeraWhatsApp = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.WHATSAPP, "+5491140801782"), desperfectoHeladera);
    notificador.asunto("Alerta de suscripción");
  }

  @Test
  public void cumpleCondicionTest() throws IOException {
    heladera1.setCantViandas(22);

    Boolean muchasViandas = suscripcionMuchasViandasCorreo.cumpleCondicion();
    Boolean pocaViandas = suscripcionPocasViandasTelegram.cumpleCondicion();
    Boolean desperfectoHeladera = suscripcionDesperfectoHeladeraTelegram1.cumpleCondicion();
    Boolean desperfectoHeladera2 = suscripcionDesperfectoHeladeraTelegram2.cumpleCondicion();


    assertEquals(true, muchasViandas, "Valor de cumpleCondicion() para muchas viandas donde CUMPLE");
    assertEquals(false, pocaViandas, "Valor de cumple condicion para pocas viandas donde NO CUMPLE");
    assertEquals(false, desperfectoHeladera, "Valor de cumple condicion para desperfecto de heladera donde NO CUMPLE");
    assertEquals(true, desperfectoHeladera2, "Valor de cumple condicion para desperfecto de heladera donde CUMPLE");

    heladera1.setCantViandas(1);
    muchasViandas = suscripcionMuchasViandasCorreo.cumpleCondicion();
    pocaViandas = suscripcionPocasViandasTelegram.cumpleCondicion();
    assertEquals(false, muchasViandas, "Valor de cumple condicion para muchas viandas donde NO CUMPLE");
    assertEquals(true, pocaViandas, "Valor de cumple condicion para pocas viandas donde CUMPLE");
  }

  @Test
  public void agregadoDeViandasCorreoTest() throws IOException {
    heladera1.setCantViandas(1);
    heladera1.agregarSuscripcion(suscripcionMuchasViandasCorreo);
    // EL VALOR TOMADO COMO MUCHO ES DE 21
    heladera1.agregarViandas(1);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.agregarViandas(15);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.agregarViandas(4);
    // DEBERIA DE LLAMARLO
  }

  @Test
  public void agregadoDeViandasTelegramTest() throws IOException {
    heladera1.setCantViandas(1);
    heladera1.agregarSuscripcion(suscripcionMuchasViandasTelegram);
    // EL VALOR TOMADO COMO MUCHO ES DE 21
    heladera1.agregarViandas(1);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.agregarViandas(15);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.agregarViandas(4);
    // DEBERIA DE LLAMARLO
  }

  // FUNCIONA, LO COMENTO PQ TE COBRA
//    @Test
//    public void agregadoDeViandasWPPTest() throws IOException {
//        heladera1.setCantViandas(1);
//        heladera1.agregarSuscripcion(suscripcionMuchasViandasWhatsApp);
//        // EL VALOR TOMADO COMO MUCHO ES DE 21
//        heladera1.agregarViandas(1);
//        System.out.println("NO LLAMA AL NOTIFICADOR");
//        heladera1.agregarViandas(15);
//        System.out.println("NO LLAMA AL NOTIFICADOR");
//        heladera1.agregarViandas(4);
//        // DEBERIA DE LLAMARLO
//    }

  @Test
  public void sacarViandasCorreoTest() throws IOException {
    heladera1.setCantViandas(15);
    heladera1.agregarSuscripcion(suscripcionPocasViandasCorreo);
    // EL VALOR TOMADO COMO POCO ES 3
    heladera1.sacarViandas(5);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.sacarViandas(5);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.sacarViandas(2);
    // DEBERIA LLAMARLO
  }

  @Test
  public void sacarViandasTelegramTest() throws IOException {
    heladera1.setCantViandas(15);
    heladera1.agregarSuscripcion(suscripcionPocasViandasTelegram);
    // EL VALOR TOMADO COMO POCO ES 3
    heladera1.sacarViandas(5);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.sacarViandas(5);
    System.out.println("NO LLAMA AL NOTIFICADOR");
    heladera1.sacarViandas(2);
    // DEBERIA LLAMARLO
  }


  // FUNCIONA, LO COMENTO PQ TE COBRA
//    @Test
//    public void sacarViandasWPPTest() throws IOException {
//        heladera1.setCantViandas(15);
//        heladera1.agregarSuscripcion(suscripcionPocasViandasWhatsApp);
//        // EL VALOR TOMADO COMO POCO ES 3
//        heladera1.sacarViandas(5);
//        System.out.println("NO LLAMA AL NOTIFICADOR");
//        heladera1.sacarViandas(5);
//        System.out.println("NO LLAMA AL NOTIFICADOR");
//        heladera1.sacarViandas(2);
//        // DEBERIA LLAMARLO
//    }

  @Test
  public void cambioEstadoCorreoTest() throws IOException {
    heladera1.agregarSuscripcion(suscripcionDesperfectoHeladeraCorreo);
    heladera1.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
    // DEBERIA DE LLAMARLO
  }

  @Test
  public void cambioEstadoTelegramTest() throws IOException {
    heladera1.agregarSuscripcion(suscripcionDesperfectoHeladeraTelegram1);
    heladera1.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
    // DEBERIA DE LLAMARLO
  }


  // FUNCIONA, LO COMENTO PQ TE COBRA
//    @Test
//    public void cambioEstadoWPPTest() throws IOException {
//        heladera1.agregarSuscripcion(suscripcionDesperfectoHeladeraWhatsApp);
//        heladera1.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
//        // DEBERIA DE LLAMARLO
//    }


}
