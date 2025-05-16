package ar.edu.utn.frba.dds.CargaMasiva;

public class CargaMasivaAdapterTest {
  /*
  private CargaMasivaAdapter cargaMasivaAdapter;

  @Before
  public void setUp() {
    cargaMasivaAdapter = new CargaMasivaAdapter();
  }

  @Test
  public void testCargarDatosCSV() {
    String archivoCSV = "src/test/resources/Contribuciones.csv";
    cargaMasivaAdapter.cargarDatosCSV(archivoCSV);

    List<Colaborador> personasContribuidoras = cargaMasivaAdapter.getPersonasContribuidoras();
    assertNotNull(personasContribuidoras);
    assertFalse(personasContribuidoras.isEmpty());

    // Imprime los datos cargados
    System.out.println("Datos cargados desde el archivo CSV:");
    for (Colaborador persona : personasContribuidoras) {
      System.out.println(persona.getNombre() + " " + persona.getApellido());
    }

    // Datos esperados
    List<Colaborador> datosEsperados = Arrays.asList(
        new Colaborador("DNI", "12345678", "Pedro", "Picapiedra", "laibarra@frba.utn.edu.ar", "2024/01/01", "DINERO", 100,null),
        new Colaborador("DNI", "87654321", "Vilma", "Picapiedra", "laibarra@frba.utn.edu.ar", "2023/01/01", "DONACION_VIANDAS", 50,null)
    );

    // Verifica que los datos cargados coinciden con los datos esperados
    for (int i = 0; i < personasContribuidoras.size(); i++) {
      Colaborador personaCargada = personasContribuidoras.get(i);
      Colaborador personaEsperada = datosEsperados.get(i);

      assertNotNull(personaCargada);
      assertEquals(personaCargada.getTipoDoc(), personaEsperada.getTipoDoc());
      assertEquals(personaCargada.getDocumento(), personaEsperada.getDocumento());
      assertEquals(personaCargada.getNombre(), personaEsperada.getNombre());
      assertEquals(personaCargada.getApellido(), personaEsperada.getApellido());
      assertEquals(personaCargada.getEmail(), personaEsperada.getEmail());
      assertEquals(personaCargada.getFechaContribucion(), personaEsperada.getFechaContribucion());
      assertEquals(personaCargada.getFormaContribucion(), personaEsperada.getFormaContribucion());
      assertEquals(personaCargada.getCantidad(), personaEsperada.getCantidad());
    }
  }
   */
}