package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Direccion.Direccion;

public class RepositorioDeDirecciones implements WithSimplePersistenceUnit {

  public void guardar(Direccion direccion) {
    withTransaction(() -> {
      entityManager().persist(direccion);
    });
  }

  public void modificar(Direccion direccion) {
    withTransaction(() -> {
      entityManager().merge(direccion);//UPDATE
    });
  }

  public void eliminarFisico(Direccion direccion) {
    entityManager().remove(direccion);//DELETE
  }

  public void eliminar(Direccion direccion) {
    direccion.setActivo(false);
    entityManager().merge(direccion);
  }

  public Optional<Direccion> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(Direccion.class, id));
  }

  public Direccion buscarPorId2(Long id) {
    return entityManager().find(Direccion.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Direccion> buscarTodos() {
    return entityManager()
        .createQuery("from " + Direccion.class.getName() + " where activo = true")
        .getResultList();
  }

}
