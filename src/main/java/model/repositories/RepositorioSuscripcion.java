package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Suscripcion.Suscripcion;

public class RepositorioSuscripcion implements WithSimplePersistenceUnit {

  public void guardar(Suscripcion suscripcion) {
    withTransaction(() -> {
      entityManager().persist(suscripcion);
    });
  }

  public Optional<Suscripcion> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Suscripcion.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Suscripcion> buscarTodos() {
    return entityManager()
        .createQuery("from " + Suscripcion.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Suscripcion> buscarTodos(Long colaborador_id) {
    return entityManager()
        .createQuery("from " + Suscripcion.class.getName() + " s where s.colaborador.id = :colaborador_id")
        .setParameter("colaborador_id", colaborador_id)
        .getResultList();
  }

  public void modificar(Suscripcion suscripcion) {
    withTransaction(() -> {
      entityManager().merge(suscripcion);
    });
  }

  public void eliminarFisico(Suscripcion suscripcion) {
    withTransaction(() -> {
      if (entityManager().contains(suscripcion)) {
        entityManager().remove(suscripcion);
      } else {
        // Si el suscripcion no está gestionado, busca y elimina por ID
        Suscripcion managedSuscripcion = entityManager().find(Suscripcion.class, suscripcion.getId());
        if (managedSuscripcion != null) {
          entityManager().remove(managedSuscripcion);
        }
      }
    });
  }

  public void eliminar(Suscripcion suscripcion) {
    suscripcion.setActivo(false);
    modificar(suscripcion);
  }
}

