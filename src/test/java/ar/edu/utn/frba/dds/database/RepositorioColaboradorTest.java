package ar.edu.utn.frba.dds.database;


import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioColaboradorTest implements WithSimplePersistenceUnit {
}
  /*
  private RepositorioColaborador repositorioColaborador;

  @BeforeEach
  public void setUp() {
    repositorioColaborador = new RepositorioColaborador();
  }

  @Test
  public void testGuardar() {
    // Crear un nuevo colaborador
    Colaborador colaborador = new Colaborador();
    colaborador.setNombre("Juan Perez");
    colaborador.setActivo(true);

    // Guardar el colaborador
    repositorioColaborador.guardar(colaborador);

    // Verificar que se generó el ID
    assertNotNull(colaborador.getId(), "El ID del colaborador no debe ser nulo después de guardar.");

    // También puedes verificar que el colaborador se puede buscar después de guardarlo
    Optional<Colaborador> colaboradorBuscado = repositorioColaborador.buscarPorID(colaborador.getId());
    assertTrue(colaboradorBuscado.isPresent(), "El colaborador guardado debe ser encontrado.");
    assertEquals("Juan Perez", colaboradorBuscado.get().getNombre(), "El nombre del colaborador no coincide.");
  }
}


//  @Test
//  public void testBuscarPorID() {
//    // Insertar un colaborador de prueba
//    Colaborador colaborador = new Colaborador();
//    colaborador.setNombre("Juan Perez");
//    colaborador.setActivo(true);
//
//    beginTransaction();
//    repositorioColaborador.guardar(colaborador);
//    commitTransaction();
//
//    // Buscar por ID
//    Optional<Colaborador> resultado = repositorioColaborador.buscarPorID(colaborador.getId());
//
//    assertTrue(resultado.isPresent());
//    assertEquals("Juan Perez", resultado.get().getNombre());
//  }
//
//  @Test
//  public void testBuscarTodos() {
//    // Insertar dos colaboradores
//    Colaborador colaborador1 = new Colaborador();
//    colaborador1.setNombre("Juan Perez");
//    colaborador1.setActivo(true);
//
//    Colaborador colaborador2 = new Colaborador();
//    colaborador2.setNombre("Maria Garcia");
//    colaborador2.setActivo(true);
//
//    beginTransaction();
//    repositorioColaborador.guardar(colaborador1);
//    repositorioColaborador.guardar(colaborador2);
//    commitTransaction();
//
//    // Buscar todos
//    List<Colaborador> colaboradores = repositorioColaborador.buscarTodos();
//
//    assertEquals(2, colaboradores.size());
//  }
//
//  @Test
//  public void testBuscarPorNombre() {
//    // Insertar colaborador
//    Colaborador colaborador = new Colaborador();
//    colaborador.setNombre("Juan Perez");
//    colaborador.setActivo(true);
//
//    beginTransaction();
//    repositorioColaborador.guardar(colaborador);
//    commitTransaction();
//
//    // Buscar por nombre
//    List<Colaborador> resultado = repositorioColaborador.buscarPorNombre("Juan Perez");
//
//    assertFalse(resultado.isEmpty());
//    assertEquals(1, resultado.size());
//    assertEquals("Juan Perez", resultado.get(0).getNombre());
//  }
//
//  @Test
//  public void testModificar() {
//    // Insertar colaborador
//    Colaborador colaborador = new Colaborador();
//    colaborador.setNombre("Juan Perez");
//    colaborador.setActivo(true);
//
//    beginTransaction();
//    repositorioColaborador.guardar(colaborador);
//    commitTransaction();
//
//    // Modificar
//    colaborador.setNombre("Juan Actualizado");
//
//    beginTransaction();
//    repositorioColaborador.modificar(colaborador);
//    commitTransaction();
//
//
//    // Buscar y verificar el cambio
//    Optional<Colaborador> resultado = repositorioColaborador.buscarPorID(colaborador.getId());
//    assertTrue(resultado.isPresent());
//    assertEquals("Juan Actualizado", resultado.get().getNombre());
//  }
//
//  @Test
//  public void testEliminarFisico() {
//    // Insertar colaborador
//    Colaborador colaborador = new Colaborador();
//    colaborador.setNombre("Juan Perez");
//    colaborador.setActivo(true);
//
//    beginTransaction();
//    repositorioColaborador.guardar(colaborador);
//    commitTransaction();
//    // Eliminar
//    beginTransaction();
//    repositorioColaborador.eliminarFisico(colaborador);
//    commitTransaction();
//
//    // Verificar que fue eliminado
//    Optional<Colaborador> resultado = repositorioColaborador.buscarPorID(colaborador.getId());
//    assertFalse(resultado.isPresent());
//  }

   */


