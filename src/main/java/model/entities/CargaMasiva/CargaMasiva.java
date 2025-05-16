package model.entities.CargaMasiva;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import model.entities.Colaborador.Colaborador;
import model.repositories.RepositorioColaborador;


@Getter
@Setter
public class CargaMasiva {

  private List<Colaborador> personasContribuidoras = new ArrayList<>();
  private Long id;
  private String nombreArchivo;
  private String rutaArchivo;
  private LocalDateTime fechaCarga;

  public CargaMasiva() {}

  public List<Colaborador> cargarDatosCSV(String archivoCSV) throws IOException {

    // Cargar y procesar los datos CSV
    CargaDatosCSV cargaDatosCSV = new CargaDatosCSV();
    personasContribuidoras = cargaDatosCSV.cargarPersonasContribuidoras(archivoCSV);

    System.out.println("Se cargaron los datos correctamente. Total de colaboradores cargados: " + personasContribuidoras.size());
    return personasContribuidoras;
  }
}