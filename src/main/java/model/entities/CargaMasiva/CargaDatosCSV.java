package model.entities.CargaMasiva;

import config.ServiceLocator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.entities.MedioDeContacto.MedioDeContacto;
import model.entities.MedioDeContacto.TipoContacto;
import model.entities.Utils.TipoDocumento;
import model.repositories.RepositorioColaborador;
import model.entities.Colaborador.Colaborador;
import model.entities.Colaborador.FormaDeContribucion;
import model.entities.Colaborador.TipoPersona;
import model.entities.Contribucion.Contribucion;
import model.entities.Contribucion.DistribucionVianda.DistribucionViandas;
import model.entities.Contribucion.DonacionDinero.DonacionDinero;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;
import model.entities.Contribucion.HacerseCargoHeladera.HacerseCargoHeladera;
import model.entities.Contribucion.RealizarOfertas.RealizarOfertas;
import model.entities.Contribucion.RegistroPersonaEnSituacionVulnerable.RegistroPersonaVulnerable;
import model.repositories.RepositorioTipoPersona;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CargaDatosCSV {

  public CargaDatosCSV() {
  }

  public List<Colaborador> cargarPersonasContribuidoras(String archivoCSV) throws IOException {
    List<Colaborador> personasContribuidoras = new ArrayList<>();

    try (FileReader reader = new FileReader(archivoCSV);
         CSVParser csvParser = new CSVParser(reader,
             CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader().withTrim())) {

      for (CSVRecord record : csvParser) {
        System.out.println("Procesando línea: " + record);
        System.out.println("Cantidad de columnas: " + record.get(0).split(";").length);

        // Validar si la línea tiene al menos 9 columnas
        if (record.get(0).split(";").length < 8) {
          System.out.println("Línea inválida (menos de 8 columnas): " + record);
          continue;
        }

        try {
          ColaboradorContribucionCSV colaboradorContribucionCSV = procesarRegistroCSV(record.get(0));
          crearContribuciones(colaboradorContribucionCSV);
          personasContribuidoras.add(colaboradorContribucionCSV.getColaborador());
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
      return personasContribuidoras;
    }
  }

  private ColaboradorContribucionCSV procesarRegistroCSV(String record) {
    try {
      String[] columnas = record.split(";");

      // Validación de cada columna
      TipoDocumento tipoDoc;
      try {
        tipoDoc = TipoDocumento.valueOf(columnas[0].trim());
      } catch (IllegalArgumentException e) {
        System.out.println("Tipo de documento inválido en la línea: " + record);
        return null;
      }

      String documento = columnas[1].trim();
      if (documento.isEmpty()) {
        System.out.println("Documento vacío en la línea: " + record);
        return null;
      }

      String nombre = columnas[2].trim();
      if (nombre.isEmpty()) {
        System.out.println("Nombre vacío en la línea: " + record);
        return null;
      }

      String apellido = columnas[3].trim();
      if (apellido.isEmpty()) {
        System.out.println("Apellido vacío en la línea: " + record);
        return null;
      }

      String email = columnas[4].trim();
      if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
        System.out.println("Email inválido en la línea: " + record);
        return null;
      }

      MedioDeContacto medioDeContacto = new MedioDeContacto(TipoContacto.CORREO, email);

      LocalDate fechaColaboracion;
      try {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        fechaColaboracion = LocalDate.parse(columnas[5].trim(), formato);
      } catch (Exception e) {
        System.out.println("Fecha de colaboración inválida en la línea: " + record);
        return null;
      }

      FormaDeContribucion formaDeContribucion;
      try {
        formaDeContribucion = FormaDeContribucion.valueOf(columnas[6].trim());
      } catch (IllegalArgumentException e) {
        System.out.println("Forma de contribución inválida en la línea: " + record);
        return null;
      }

      int cantidad;
      try {
        cantidad = Integer.parseInt(columnas[7].trim());
      } catch (NumberFormatException e) {
        System.out.println("Cantidad inválida en la línea: " + record);
        return null;
      }

      // Creación del colaborador y contribución
      Colaborador colaborador = new Colaborador(nombre, apellido, medioDeContacto, tipoDoc, documento);
      ContribucionCSV contribucionCSV = new ContribucionCSV(fechaColaboracion, formaDeContribucion, cantidad);

      return new ColaboradorContribucionCSV(colaborador, contribucionCSV);

    } catch (Exception e) {
      // Registro de errores y manejo general de excepciones
      System.out.println("Error al procesar el registro: " + record);
      e.printStackTrace();
    }
    return null;
  }

  private void crearContribuciones(ColaboradorContribucionCSV colaboradorContribucionCSV) {

    List<Contribucion> contribuciones = new ArrayList<>();

    try {
      switch (colaboradorContribucionCSV.getContribucionCSV().getFormaDeContribucion()) {
        case DonacionDinero:
          // Solo una instancia para DonacionDinero
          Contribucion donacionDinero = new DonacionDinero(
              colaboradorContribucionCSV.getColaborador(),
              colaboradorContribucionCSV.getContribucionCSV().getFechaDeContribucion().atStartOfDay(),
              colaboradorContribucionCSV.getContribucionCSV().getCantidad()
          );
          contribuciones.add(donacionDinero);
          break;

        case DonacionVianda:
          for (int i = 0; i < colaboradorContribucionCSV.getContribucionCSV().getCantidad(); i++) {
            Contribucion donacionVianda = new DonacionVianda(colaboradorContribucionCSV.getContribucionCSV().getFechaDeContribucion().atStartOfDay() );
            contribuciones.add(donacionVianda);
          }
          break;

        case DistribucionDeViandas:
            Contribucion distribucionViandas = new DistribucionViandas(colaboradorContribucionCSV.getContribucionCSV().getFechaDeContribucion().atStartOfDay(),colaboradorContribucionCSV.getContribucionCSV().getCantidad());
            contribuciones.add(distribucionViandas);
          break;

        case RegistroPersonaEnSituacionVulnerable:
          for (int i = 0; i < colaboradorContribucionCSV.getContribucionCSV().getCantidad(); i++) {
            Contribucion registroVulnerable = new RegistroPersonaVulnerable(colaboradorContribucionCSV.getContribucionCSV().getFechaDeContribucion().atStartOfDay());
            contribuciones.add(registroVulnerable);
          }
          break;

        default:
          System.out.println("Forma de contribución no válida.");
          break;
      }

      for (Contribucion contribucion : contribuciones) {
        System.out.println("Contribución creada con éxito: " + contribucion);
        colaboradorContribucionCSV.getColaborador().agregarContribucion(contribucion);
      }

    } catch (Exception e) {
      System.err.println("Error al crear la contribución para el colaborador con documento " +
          colaboradorContribucionCSV.getColaborador().getDocumento() + ": " + e.getMessage());
    }

  }


}