package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Suscripcion.TipoSuscripcion;

public class RepositorioTipoSuscripcion implements WithSimplePersistenceUnit {
  public void guardar(TipoSuscripcion tipoSuscripcion) {
    withTransaction(() -> {
      entityManager().persist(tipoSuscripcion);
    });
  }

  public Optional<TipoSuscripcion> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(TipoSuscripcion.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<TipoSuscripcion> buscarTodos() {
    return entityManager()
        .createQuery("from " + TipoSuscripcion.class.getName())
        .getResultList();
  }

  public void modificar(TipoSuscripcion tipoSuscripcion) {
    withTransaction(() -> {
      entityManager().merge(tipoSuscripcion);
    });
  }

  public void eliminarFisico(TipoSuscripcion tipoSuscripcion) {
    entityManager().remove(tipoSuscripcion);
  }

  public void eliminar(TipoSuscripcion tipoSuscripcion) {
    tipoSuscripcion.setActivo(false);
    modificar(tipoSuscripcion);
  }
}
