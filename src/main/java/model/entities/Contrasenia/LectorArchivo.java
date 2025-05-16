package model.entities.Contrasenia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorArchivo {
  private static LectorArchivo instance;
  private String fileContent;

  private LectorArchivo() {
    // Constructor privado para evitar instanciación directa
  }

  public static LectorArchivo getInstance() {
    if (instance == null) {
      instance = new LectorArchivo();
    }
    return instance;
  }

  public boolean existeEnArchivo(String filePath, String word) {
    // Leer el archivo cada vez que se llama al método
    readFile(filePath);

    return fileContent.contains(word);
  }

  private void readFile(String filePath) {
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        contentBuilder.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    fileContent = contentBuilder.toString();
  }
}


// leo una sola vez y lo guardo en una variable estatica
// usar un singleton para devolver la misma instancia, leo el archivo una sola vez y devuelvo la misma instancia siempre
// comparo siempre con la misma instancia

