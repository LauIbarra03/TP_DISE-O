package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import model.entities.Direccion.Provincia;

public class RepositorioProvincia implements WithSimplePersistenceUnit {

  public void guardar(Provincia provincia) {
    withTransaction(() -> {
      entityManager().persist(provincia);
    });
  }

  public Optional<Provincia> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Provincia.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Provincia> buscarTodos() {
    return entityManager()
        .createQuery("from " + Provincia.class.getName())
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Provincia> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Provincia.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public Provincia buscarPorNombreUnico(String nombre) {
    try {
      return entityManager()
          .createQuery("from " + Provincia.class.getName() + " where nombre = :name", Provincia.class)
          .setParameter("name", nombre)
          .getSingleResult();
    } catch (NoResultException e) {
      return null; // Retorna null si no se encuentra ninguna provincia con el nombre dado
    }
  }

  public void modificar(Provincia provincia) {
    withTransaction(() -> {
      entityManager().merge(provincia);
    });
  }

  public void eliminarFisico(Provincia provincia) {
    withTransaction(() -> {
      if (entityManager().contains(provincia)) {
        entityManager().remove(provincia);
      } else {
        // Si el provincia no está gestionado, busca y elimina por ID
        Provincia managedProvincia = entityManager().find(Provincia.class, provincia.getId());
        if (managedProvincia != null) {
          entityManager().remove(managedProvincia);
        }
      }
    });
  }

  public void eliminar(Provincia provincia) {
    provincia.setActivo(false);
    modificar(provincia);
  }
}

