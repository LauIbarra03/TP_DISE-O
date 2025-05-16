package model.entities.Contrasenia.exception;

public class ContraseniaIgualAUsuarioException extends RuntimeException {
  public ContraseniaIgualAUsuarioException() {
    super("La contraseña no debe ser igual que el nombre de usuario");
  }
}
