package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Direccion.Calle;

public class RepositorioCalle implements WithSimplePersistenceUnit {

  public void guardar(Calle calle) {
    withTransaction(() -> {
      entityManager().persist(calle);
    });
  }

  public Optional<Calle> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Calle.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Calle> buscarTodos() {
    return entityManager()
        .createQuery("from " + Calle.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Calle> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Calle.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(Calle calle) {
    withTransaction(() -> {
      entityManager().merge(calle);
    });
  }

  public void eliminarFisico(Calle calle) {
    withTransaction(() -> {
      if (entityManager().contains(calle)) {
        entityManager().remove(calle);
      } else {
        // Si el calle no está gestionado, busca y elimina por ID
        Calle managedCalle = entityManager().find(Calle.class, calle.getId());
        if (managedCalle != null) {
          entityManager().remove(managedCalle);
        }
      }
    });
  }

  public void eliminar(Calle calle) {
    calle.setActivo(false);
    modificar(calle);
  }
}

