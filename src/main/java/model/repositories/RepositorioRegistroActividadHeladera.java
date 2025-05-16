package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Heladera.RegistroHeladera;

public class RepositorioRegistroActividadHeladera implements WithSimplePersistenceUnit {
  public void guardar(RegistroHeladera registroHeladera) {
    withTransaction(() -> {
      entityManager().persist(registroHeladera);
    });
  }

  public Optional<RegistroHeladera> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(RegistroHeladera.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<RegistroHeladera> buscarTodos() {
    return entityManager()
        .createQuery("from " + RegistroHeladera.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<RegistroHeladera> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + RegistroHeladera.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(RegistroHeladera registroHeladera) {
    withTransaction(() -> {
      entityManager().merge(registroHeladera);
    });
  }

  public void eliminarFisico(RegistroHeladera registroHeladera) {
    withTransaction(() -> {
      if (entityManager().contains(registroHeladera)) {
        entityManager().remove(registroHeladera);
      } else {
        RegistroHeladera managedRegistroHeladera = entityManager().find(RegistroHeladera.class, registroHeladera.getId());
        if (managedRegistroHeladera != null) {
          entityManager().remove(managedRegistroHeladera);
        }
      }
    });
  }

  public void eliminar(RegistroHeladera registroHeladera) {
    registroHeladera.setActivo(false);
    modificar(registroHeladera);
  }
}