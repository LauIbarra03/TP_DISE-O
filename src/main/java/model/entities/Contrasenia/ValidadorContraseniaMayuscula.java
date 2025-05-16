package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaSinMayusculaException;
import model.entities.Usuario.Usuario;

public class ValidadorContraseniaMayuscula implements Validaciones {

  @Override
  public void validarContrasenia(Usuario usuario, String contrasenia) {
    if (contrasenia.chars().filter(Character::isUpperCase).count() == 0) {
      throw new ContraseniaSinMayusculaException();
    }
  }
}
