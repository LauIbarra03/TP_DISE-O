package ar.edu.utn.frba.dds.Notificador;

public class NotificadorTest {
    /*
    private Notificador notificador;
    private EnviadorWPPAdapter enviarWPP;
    private EnviadorMailAdapter enviadorMailAdapter;
    private EnviadorTelegramAdapter enviadorTelegramAdapter;
    private Usuario usuario;
    private Heladera heladera1, heladera2, heladera3;
    private Suscripcion suscripcion1, suscripcion2, suscripcion3;
    @BeforeEach
    public void setUp() throws IOException, TelegramApiException {
        notificador = Notificador.getInstance();
        enviarWPP = EnviadorWPPAdapter.getInstance();
        enviadorMailAdapter = EnviadorMailAdapter.getInstance();
        enviadorTelegramAdapter = EnviadorTelegramAdapter.getInstance();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(enviadorTelegramAdapter);

        usuario = new Usuario();
        usuario.setMail("laibarra@frba.utn.edu.ar");

        heladera1 =
            new Heladera(new PuntoGeografico(1,
                1,
                new Direccion(
                    new Calle("Av. Amancio Alcorta"),
                    2570,
                    1,
                    new Ciudad("a"),
                    new Provincia("a"),
                    new Localidad("a")),"a"),
                20,
                LocalDate.now(),
                EstadoHeladera.ACTIVA);
        MuchasViandas muchasViandas = new MuchasViandas(20);
        PocasViandas pocasViandas = new PocasViandas(2);
        DesperfectoHeladera desperfectoHeladera = new DesperfectoHeladera();
        suscripcion1 = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.CORREO, "laibarra@frba.utn.edu.ar"), muchasViandas);
        suscripcion2 = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.TELEGRAM, "5827432205"), pocasViandas);
        suscripcion3 = new Suscripcion(heladera1, new MedioDeContacto(TipoContacto.WHATSAPP, "+5491140801782"), desperfectoHeladera);
        notificador.asunto("Alerta de suscripción");
    }

    @Test
    public void envioWPPTest() throws IOException {
        // Te cobra esto, lo descomentamos para mostrar en la entrega
        notificador.enviarWPP("+5491140801782", "Mensaje de prueba");
    }

    // ESTE MAIN ESTA CREADO PARA QUE EL BOT QUEDE EJECUTANDO Y PUEDA RECIBIR Y RESPONDER MENSAJES
    public static void main(String[] args) throws TelegramApiException{
        EnviadorTelegramAdapter enviadorTelegramAdapter = EnviadorTelegramAdapter.getInstance();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(enviadorTelegramAdapter);

        enviadorTelegramAdapter.enviar("5827432205", "Mensaje de prueba");
    }
    @Test
    public void envioTelegramTest() throws IOException {
        notificador.enviarTelegram("5827432205", "Mensaje de prueba");
    }
    @Test
    public void envioMailTest() {
        notificador.asunto("Prueba");

        notificador.enviarMail(usuario.getMail(),"MAIL PRUEBA");
    }

    @Test
    public void llamadoNotificadorDesdeSuscripcionTest() throws IOException {
        // suscripcion1.llamarNotificador(); // LO MANDA
        // suscripcion2.llamarNotificador(); // LO MANDA
        // suscripcion3.llamarNotificador(); // LO MANDA, NO SE PQ AL PRINCIPIO NO FUNCIONABA, PROBAR ANTES DE LA ENTREGA
    }

     */
}
