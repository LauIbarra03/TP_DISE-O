package model.entities.Contrasenia.exception;

public class ContraseniaCortaException extends RuntimeException {
  public ContraseniaCortaException(int largoMinimo) {
    super("El largo de la contraseña debe superar " + largoMinimo + " caractéres");
  }
}
