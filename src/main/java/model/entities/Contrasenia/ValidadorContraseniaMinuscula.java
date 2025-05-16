package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaSinMinusculaException;
import model.entities.Usuario.Usuario;

public class ValidadorContraseniaMinuscula implements Validaciones {

  @Override
  public void validarContrasenia(Usuario usuario, String contrasenia) {
    if (contrasenia.chars().filter(Character::isLowerCase).count() == 0) {
      throw new ContraseniaSinMinusculaException();
    }
  }
}
