package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaSinNumeroException;
import model.entities.Usuario.Usuario;

public class ValidadorContraseniaNumero implements Validaciones {

  public void validarContrasenia(Usuario usuario, String contrasenia) {
    if (contrasenia.chars().filter(Character::isDigit).count() == 0) {
      throw new ContraseniaSinNumeroException();
    }
  }
}
