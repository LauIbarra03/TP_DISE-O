package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.PuntoGeografico.PuntoGeografico;

public class RepositorioPuntoGeografico implements WithSimplePersistenceUnit {

  public void guardar(PuntoGeografico puntoGeografico) {
    withTransaction(() -> {
      entityManager().persist(puntoGeografico);
    });
  }

  public void modificar(PuntoGeografico puntoGeografico) {
    withTransaction(() -> {
      entityManager().merge(puntoGeografico);//UPDATE
    });
  }

  public void eliminarFisico(PuntoGeografico puntoGeografico) {
    entityManager().remove(puntoGeografico);//DELETE
  }

  public void eliminar(PuntoGeografico puntoGeografico) {
    puntoGeografico.setActivo(false);
    entityManager().merge(puntoGeografico);
  }

  public Optional<PuntoGeografico> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(PuntoGeografico.class, id));
  }

  public PuntoGeografico buscarPorId2(Long id) {
    return entityManager().find(PuntoGeografico.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<PuntoGeografico> buscarTodos() {
    return entityManager()
        .createQuery("from " + PuntoGeografico.class.getName())
        .getResultList();
  }
}
