package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Vianda.Comida;


public class RepositorioComida implements WithSimplePersistenceUnit {

  public void guardar(Comida comida) {
    try {
      withTransaction(() -> {
        entityManager().persist(comida);
      });
    } catch (Exception e) {
      e.printStackTrace(); // O maneja el error de otra manera
    }
  }

  public void modificar(Comida comida) {
    withTransaction(() -> {
      entityManager().merge(comida);//UPDATE
    });
  }

  public void eliminarFisico(Comida comida) {
    entityManager().remove(comida);//DELETE
  }

  public void eliminar(Comida comida) {
    comida.setActivo(false);
    entityManager().merge(comida);
  }

  public Optional<Comida> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(Comida.class, id));
  }

  public Comida buscarPorId2(Long id) {
    return entityManager().find(Comida.class, id);
  }

  public Comida buscarPorNombre(String nombre) {
    String jpql = "SELECT c FROM Comida c WHERE c.nombre = :nombre";
    List<Comida> resultados = entityManager()
        .createQuery(jpql, Comida.class)
        .setParameter("nombre", nombre)
        .getResultList();

    // Devuelve el primer resultado o null si no se encontró ninguno
    return resultados.isEmpty() ? null : resultados.get(0);
  }

  @SuppressWarnings("unchecked")
  public List<Comida> buscarTodos() {
    return entityManager()
        .createQuery("from " + Comida.class.getName() + " where activo = true")
        .getResultList();
  }
}