package model.entities.Contrasenia.exception;

public class ContraseniaSinNumeroException extends RuntimeException {
  public ContraseniaSinNumeroException() {
    super("La contraseña debe contener un número");
  }
}