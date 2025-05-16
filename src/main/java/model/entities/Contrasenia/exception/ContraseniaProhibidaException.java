package model.entities.Contrasenia.exception;

public class ContraseniaProhibidaException extends RuntimeException {
  public ContraseniaProhibidaException() {
    super("La contraseña está prohibida para su utilizacón");
  }
}
