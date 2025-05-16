package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaCortaException;
import model.entities.Usuario.Usuario;

public class ValidadorContraseniaLongitud implements Validaciones {
  public static final int LARGO_MINIMO_CONTRASENIA = 8;

  @Override
  public void validarContrasenia(Usuario usuario, String contrasenia) {
    if (contrasenia.length() < LARGO_MINIMO_CONTRASENIA) {
      throw new ContraseniaCortaException(LARGO_MINIMO_CONTRASENIA);
    }
  }
}
