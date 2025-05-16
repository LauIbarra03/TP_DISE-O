package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Direccion.Ciudad;

public class RepositorioCiudad implements WithSimplePersistenceUnit {

  public void guardar(Ciudad ciudad) {
    withTransaction(() -> {
      entityManager().persist(ciudad);
    });
  }

  public Optional<Ciudad> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Ciudad.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Ciudad> buscarTodos() {
    return entityManager()
        .createQuery("from " + Ciudad.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Ciudad> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Ciudad.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(Ciudad ciudad) {
    withTransaction(() -> {
      entityManager().merge(ciudad);
    });
  }

  public void eliminarFisico(Ciudad ciudad) {
    withTransaction(() -> {
      if (entityManager().contains(ciudad)) {
        entityManager().remove(ciudad);
      } else {
        // Si el ciudad no está gestionado, busca y elimina por ID
        Ciudad managedCiudad = entityManager().find(Ciudad.class, ciudad.getId());
        if (managedCiudad != null) {
          entityManager().remove(managedCiudad);
        }
      }
    });
  }

  public void eliminar(Ciudad ciudad) {
    ciudad.setActivo(false);
    modificar(ciudad);
  }
}

