package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaProhibidaException;
import model.entities.Usuario.Usuario;

public class ValidadorContraseniaProhibida implements Validaciones {
  public static final String PATH_PROHIBIDAS = "src/main/java/domain/Contrasenia/archivos/palabras-prohibidas.txt";
  LectorArchivo lectorArchivo = LectorArchivo.getInstance();

  @Override
  public void validarContrasenia(Usuario usuario, String contrasenia) {
    boolean buscarContrasenia = lectorArchivo.existeEnArchivo(PATH_PROHIBIDAS, contrasenia);

    if (buscarContrasenia) {
      System.out.println("La palabra " + contrasenia + " se encuentra en el archivo.");
      throw new ContraseniaProhibidaException();
    } else {
      System.out.println("La palabra " + contrasenia + " no se encuentra en el archivo.");
    }

  }
}
