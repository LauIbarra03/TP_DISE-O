package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaIgualAUsuarioException;
import model.entities.Usuario.Usuario;

public class ValidadorContraseniaUsuario implements Validaciones {
  @Override
  public void validarContrasenia(Usuario usuario, String contrasenia) {
    if (usuario.getUsername().equals(contrasenia)) {
      throw new ContraseniaIgualAUsuarioException();
    }
  }
}
