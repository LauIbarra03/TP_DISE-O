package model.entities.Contrasenia.exception;

public class ContraseniaSinMinusculaException extends RuntimeException {
  public ContraseniaSinMinusculaException() {
    super("La contraseña debe contener una letra minúscula");
  }
}
