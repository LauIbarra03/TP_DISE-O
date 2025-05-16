package model.entities.Incidente;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import model.entities.Colaborador.Colaborador;
import model.entities.Heladera.EstadoHeladera;
import model.entities.Heladera.Heladera;
import model.entities.Notificador.Notificador;
import model.entities.Tecnico.Tecnico;

public class ProcesadorDeIncidentes {
  private final Notificador notificador;

  public ProcesadorDeIncidentes() {
    this.notificador = Notificador.getInstance();
  }

  public void sucedeIncidente(TipoAlerta tipoAlerta, Heladera heladera) throws IOException {
    Incidente incidente = Incidente.builder()
        .fechaYHora(LocalDateTime.now())
        .tipoIncidente(TipoIncidente.Alerta)
        .tipoAlerta(tipoAlerta)
        .heladera(heladera)
        .estaResuelto(false)
        .build();
    heladera.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
    heladera.agregarIncidente(incidente);
    String alerta = " la deteccion de una alerta del tipo " + tipoAlerta.toString();
    avisarATecnicos(incidente, alerta);
  }

  public void sucedeIncidente(LocalDateTime fechaYHora, Heladera heladera, Colaborador colaborador,
                              String descripcion, String foto) throws IOException {
    Incidente incidente = Incidente.of(fechaYHora, TipoIncidente.FallaTecnica, heladera, colaborador, descripcion, foto);
    heladera.setEstadoHeladera(EstadoHeladera.NO_ACTIVA);
    heladera.agregarIncidente(incidente);
    String falla = " la deteccion de una falla tecnica: " + descripcion;
    avisarATecnicos(incidente, falla);

  }

  public void avisarATecnicos(Incidente incidente, String mensaje) throws IOException {
    Set<Tecnico> tecnicosCercanos = incidente.getHeladera().localidad().getTecnicos();
    for (Tecnico tecnico : tecnicosCercanos) {
      String mensajeFinal = "Se notifica al técnico " + tecnico.getNombre() + " " + tecnico.getApellido() +
          mensaje + " sobre la heladera ubicada en " +
          incidente.getHeladera().getPuntoGeografico().getDireccion().getCiudad().getNombre() +
          ", " + incidente.getHeladera().getPuntoGeografico().getDireccion().getLocalidad().getNombre();

      notificador.asunto("Alerta");
      notificador.notificar(tecnico.getMedioDeContacto(), mensajeFinal);
    }

  }
}
