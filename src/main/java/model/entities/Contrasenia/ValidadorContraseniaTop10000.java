package model.entities.Contrasenia;

import model.entities.Contrasenia.exception.ContraseniaEntreLas10000MasComunesException;
import model.entities.Usuario.Usuario;


public class ValidadorContraseniaTop10000 implements Validaciones {
  public static final String PATH_10000 = "src/main/java/domain/Contrasenia/archivos/10000-mas-comunes.txt";
  LectorArchivo lectorArchivo = LectorArchivo.getInstance();

  @Override
  public void validarContrasenia(Usuario usuario, String contrasenia) {
    boolean buscarContrasenia = lectorArchivo.existeEnArchivo(PATH_10000, contrasenia);

    if (buscarContrasenia) {
      System.out.println("La palabra " + contrasenia + " se encuentra en el archivo.");
      throw new ContraseniaEntreLas10000MasComunesException();
    } else {
      System.out.println("La palabra " + contrasenia + " no se encuentra en el archivo.");
    }

  }
}
