package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Heladera.SolicitudSobreHeladera.SolicitudSobreHeladera;


public class RepositorioSolicitudSobreHeladera implements WithSimplePersistenceUnit {

  public void guardar(SolicitudSobreHeladera solicitudSobreHeladera) {
    withTransaction(() -> {
      entityManager().persist(solicitudSobreHeladera);
    });
  }

  public Optional<SolicitudSobreHeladera> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(SolicitudSobreHeladera.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudSobreHeladera> buscarTodos() {
    return entityManager()
        .createQuery("from " + SolicitudSobreHeladera.class.getName())
        .getResultList();
  }

  public void modificar(SolicitudSobreHeladera solicitudSobreHeladera) {
    withTransaction(() -> {
      entityManager().merge(solicitudSobreHeladera);
    });
  }

  public void eliminarFisico(SolicitudSobreHeladera solicitudSobreHeladera) {
    entityManager().remove(solicitudSobreHeladera);
  }

}
