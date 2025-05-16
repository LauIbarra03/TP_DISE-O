package ar.edu.utn.frba.dds.Contrasenia;


public class ValidadorContraseniaTest {
/*
    private Usuario crearUsuario(String nombre) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        return usuario;
    }

    @Test
    public void contraseniaMenorAlLargoMinimoLanzaExcepcion() {
        String contrasenia = "corta";
        Usuario usuario = crearUsuario("Carlos");

        ContraseniaCortaException exception = assertThrows(
            ContraseniaCortaException.class,
            () -> new ValidadorContraseniaLongitud().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(
            new ContraseniaCortaException(ValidadorContraseniaLongitud.LARGO_MINIMO_CONTRASENIA).getMessage(),
            exception.getMessage()
        );
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaSinMayusculaLanzaExcepcion() {
        String contrasenia = "carlitos";
        Usuario usuario = crearUsuario("Carlos");

        ContraseniaSinMayusculaException exception = assertThrows(
            ContraseniaSinMayusculaException.class,
            () -> new ValidadorContraseniaMayuscula().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(new ContraseniaSinMayusculaException().getMessage(), exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaSinMinusculaLanzaExcepcion() {
        String contrasenia = "CARLITOS";
        Usuario usuario = crearUsuario("Carlos");

        ContraseniaSinMinusculaException exception = assertThrows(
            ContraseniaSinMinusculaException.class,
            () -> new ValidadorContraseniaMinuscula().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(new ContraseniaSinMinusculaException().getMessage(), exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaSinNumeroLanzaException() {
        String contrasenia = "Carlitos";
        Usuario usuario = crearUsuario("Carlos");

        ContraseniaSinNumeroException exception = assertThrows(
            ContraseniaSinNumeroException.class,
            () -> new ValidadorContraseniaNumero().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(new ContraseniaSinNumeroException().getMessage(), exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaIgualQueUsuarioLanzaExcepcion() {
        String contrasenia = "CarlosPerez";
        Usuario usuario = crearUsuario("CarlosPerez");

        ContraseniaIgualAUsuarioException exception = assertThrows(
            ContraseniaIgualAUsuarioException.class,
            () -> new ValidadorContraseniaUsuario().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(new ContraseniaIgualAUsuarioException().getMessage(), exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaProhibidaLanzaExcepcion() {
        String contrasenia = "AccesoAlimentario";
        Usuario usuario = crearUsuario("Carlos");

        ContraseniaProhibidaException exception = assertThrows(
            ContraseniaProhibidaException.class,
            () -> new ValidadorContraseniaProhibida().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(new ContraseniaProhibidaException().getMessage(), exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaEntreLas10000MasComunesLanzaExcepcion() {
        String contrasenia = "123456";
        Usuario usuario = crearUsuario("Carlos");

        ContraseniaEntreLas10000MasComunesException exception = assertThrows(
            ContraseniaEntreLas10000MasComunesException.class,
            () -> new ValidadorContraseniaTop10000().validarContrasenia(usuario, contrasenia)
        );

        assertEquals(new ContraseniaEntreLas10000MasComunesException().getMessage(), exception.getMessage());
        System.out.println(exception.getMessage());
    }

    @Test
    public void contraseniaCumpleRequisitos() {
        String contrasenia = "Carlos123";
        Usuario usuario = crearUsuario("Carlos");

        // Verifica que la contraseña cumple con los requisitos
        assertTrue(new ValidadorContrasenia().validarClave(usuario, contrasenia));
    }

    @Test
    public void contraseniaNoCumpleRequisitos() {
        String contrasenia = "carlos";
        Usuario usuario = crearUsuario("Carlos");

        // Verifica que la contraseña no cumple con los requisitos
        assertFalse(new ValidadorContrasenia().validarClave(usuario, contrasenia));
    }

 */

}
