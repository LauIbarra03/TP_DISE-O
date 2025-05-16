package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Tarjeta.Tarjeta;

public class RepositorioTarjeta implements WithSimplePersistenceUnit {

  public void guardar(Tarjeta tarjeta) {
    withTransaction(() -> {
      entityManager().persist(tarjeta);
    });
  }

  public Optional<Tarjeta> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Tarjeta.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Tarjeta> buscarTodos() {
    return entityManager()
        .createQuery("from " + Tarjeta.class.getName())
        .getResultList();
  }

  public void modificar(Tarjeta tarjeta) {
    withTransaction(() -> {
      entityManager().merge(tarjeta);
    });
  }

  public void eliminarFisico(Tarjeta tarjeta) {
    entityManager().remove(tarjeta);
  }

  public void eliminar(Tarjeta tarjeta) {
    tarjeta.setActivo(false);
    modificar(tarjeta);
  }
}
