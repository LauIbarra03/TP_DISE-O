package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Heladera.Modelo;


public class RepositorioDeModelos implements WithSimplePersistenceUnit {

  public void guardar(Modelo modelo) {
    withTransaction(() -> {
      entityManager().persist(modelo);
    });
  }

  public Optional<Modelo> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Modelo.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Modelo> buscarTodos() {
    return entityManager()
        .createQuery("from " + Modelo.class.getName())
        .getResultList();
  }

  public void modificar(Modelo modelo) {
    withTransaction(() -> {
      entityManager().merge(modelo);
    });
  }

  public void eliminarFisico(Modelo modelo) {
    entityManager().remove(modelo);
  }

}
