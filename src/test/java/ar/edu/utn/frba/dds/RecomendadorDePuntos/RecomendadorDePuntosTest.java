package ar.edu.utn.frba.dds.RecomendadorDePuntos;

public class RecomendadorDePuntosTest {
/*
    @Test
    public void testRecomendadorDePuntos() throws IOException {
        Punto punto1 = new Punto();
        punto1.setLatitud(40.712776);
        punto1.setLongitud(-74.005974);

        Punto punto2 = new Punto();
        punto2.setLatitud(34.052235);
        punto2.setLongitud(-118.243683);

        Punto punto3 = new Punto();
        punto3.setLatitud(51.507351);
        punto3.setLongitud(-0.127758);

        Punto punto4 = new Punto();
        punto4.setLatitud(48.856613);
        punto4.setLongitud(2.352222);

        Punto punto5 = new Punto();
        punto5.setLatitud(-33.868820);
        punto5.setLongitud(151.209290);

        List<Punto> expectedPuntos = Arrays.asList(punto1, punto2, punto3, punto4, punto5);
        ListadoDePuntos expectedListadoDePuntos = new ListadoDePuntos();
        expectedListadoDePuntos.setPuntos(expectedPuntos);

        RecomendadorPuntosAdapter recomendadorPuntosAdapter = RecomendadorPuntosAdapter.getInstance();

        RecomendadorDePuntos recomendadorDePuntos = new RecomendadorDePuntos(recomendadorPuntosAdapter);

        ListadoDePuntos listadoDePuntosRecomendados = recomendadorDePuntos.puntoDeColocacionRecomendados(-74.005974, 40.712776, 3);


        assertEquals(expectedListadoDePuntos.puntos, listadoDePuntosRecomendados.puntos);
    }

    @Test
    public void testRecomendadorDePuntosError400() throws IOException {
        RecomendadorPuntosAdapter recomendadorPuntosAdapter = RecomendadorPuntosAdapter.getInstance();
        RecomendadorDePuntos recomendadorDePuntos = new RecomendadorDePuntos(recomendadorPuntosAdapter);
        ErrorRecomendador errorRecomendador = recomendadorDePuntos.puntoDeColocacionRecomendados(1, 2);

        assertNotNull(errorRecomendador);
        assertNotNull(errorRecomendador.getError());
        assertEquals(400, errorRecomendador.getError().getCodigo());
        assertEquals("Solicitud incorrecta. Por favor, verifique los datos enviados.", errorRecomendador.getError().getMensaje());
        assertEquals(1, errorRecomendador.getError().getDetalles().size());
        assertEquals("radio", errorRecomendador.getError().getDetalles().get(0).getTipo());
        assertEquals("El valor de radio es obligatorio y debe ser un número.", errorRecomendador.getError().getDetalles().get(0).getMensaje());
    }

    @Test
    public void testRecomendadorDePuntosError404() throws IOException {
        RecomendadorPuntosAdapter recomendadorPuntosAdapter = RecomendadorPuntosAdapter.getInstance();
        RecomendadorDePuntos recomendadorDePuntos = new RecomendadorDePuntos(recomendadorPuntosAdapter);
        ErrorRecomendador errorRecomendador = recomendadorDePuntos.puntoDeColocacionRecomendados();

        assertNotNull(errorRecomendador);
        assertNotNull(errorRecomendador.getError());
        assertEquals(404, errorRecomendador.getError().getCodigo());
        assertEquals("Recurso no encontrado. La URL solicitada no existe en el servidor.", errorRecomendador.getError().getMensaje());
        assertEquals(1, errorRecomendador.getError().getDetalles().size());
        assertEquals("Recurso", errorRecomendador.getError().getDetalles().get(0).getTipo());
        assertEquals("El recurso solicitado con la URL /api/v1/puntos no se encontró.", errorRecomendador.getError().getDetalles().get(0).getMensaje());
    }

 */
}
