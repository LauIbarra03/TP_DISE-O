package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Producto.Rubro;

public class RepositorioDeRubros implements WithSimplePersistenceUnit {

  public void guardar(Rubro rubro) {
    withTransaction(() -> {
      entityManager().persist(rubro);
    });
  }

  public Optional<Rubro> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Rubro.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Rubro> buscarTodos() {
    return entityManager()
        .createQuery("from " + Rubro.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Rubro> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Rubro.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(Rubro rubro) {
    withTransaction(() -> {
      entityManager().merge(rubro);
    });
  }

  public void eliminarFisico(Rubro rubro) {
    withTransaction(() -> {
      if (entityManager().contains(rubro)) {
        entityManager().remove(rubro);
      } else {
        // Si el rubro no está gestionado, busca y elimina por ID
        Rubro managedRubro = entityManager().find(Rubro.class, rubro.getId());
        if (managedRubro != null) {
          entityManager().remove(managedRubro);
        }
      }
    });
  }

  public void eliminar(Rubro rubro) {
    rubro.setActivo(false);
    modificar(rubro);
  }
}

