package model.entities.Contrasenia.exception;

public class ContraseniaSinMayusculaException extends RuntimeException {
  public ContraseniaSinMayusculaException() {
    super("La contraseña debe contener una letra mayúscula");
  }
}
