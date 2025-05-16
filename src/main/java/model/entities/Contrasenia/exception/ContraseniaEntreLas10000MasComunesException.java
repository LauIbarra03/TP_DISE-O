package model.entities.Contrasenia.exception;

public class ContraseniaEntreLas10000MasComunesException extends RuntimeException {
  public ContraseniaEntreLas10000MasComunesException() {
    super("La contraseña está entre las 10.000 más comunes");
  }
}
