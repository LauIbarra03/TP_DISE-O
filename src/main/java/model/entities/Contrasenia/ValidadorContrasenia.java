package model.entities.Contrasenia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.entities.Usuario.Usuario;

public class ValidadorContrasenia {
  private static List<Validaciones> validables;

  public ValidadorContrasenia() {
    validables = new ArrayList<>(Arrays.asList(
        new ValidadorContraseniaLongitud(),
        new ValidadorContraseniaMayuscula(),
        new ValidadorContraseniaMinuscula(),
        new ValidadorContraseniaNumero(),
        new ValidadorContraseniaUsuario(),
        new ValidadorContraseniaTop10000()
    ));
  }

  public void validarClave(Usuario usuario, String contrasenia) {
    for (Validaciones validacion : validables) {
      validacion.validarContrasenia(usuario, contrasenia);
    }
  }
}
