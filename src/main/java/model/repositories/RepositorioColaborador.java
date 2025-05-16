package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import model.entities.Colaborador.Colaborador;
import model.entities.Utils.TipoDocumento;


public class RepositorioColaborador implements WithSimplePersistenceUnit {

  public void withTransaction(Runnable action) {
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();
      action.run();
      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw e;
    }
  }

  public void guardar(Colaborador colaborador) {
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();
      entityManager().persist(colaborador);
      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace(); // Muestra el error en caso de que ocurra
    }
  }

  public Optional<Colaborador> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Colaborador.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Colaborador> buscarTodos() {
    return entityManager()
        .createQuery("from " + Colaborador.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Colaborador> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Colaborador.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(Colaborador colaborador) {
    withTransaction(() -> {
      entityManager().merge(colaborador);
    });
  }


  public void eliminarFisico(Colaborador colaborador) {

    withTransaction(() -> {
      if (entityManager().contains(colaborador)) {
        entityManager().remove(colaborador);
      } else {
        // Si el colaborador no está gestionado, busca y elimina por ID
        Colaborador managedColaborador = entityManager().find(Colaborador.class, colaborador.getId());
        if (managedColaborador != null) {
          entityManager().remove(managedColaborador);
        }
      }
    });
  }

  public void eliminar(Colaborador colaborador) {
    colaborador.setActivo(false);
    modificar(colaborador);
  }

  public Long contarColaboradores() {
    return (Long) entityManager()
        .createQuery("SELECT COUNT(c) FROM Colaborador c " + " where activo = true")
        .getSingleResult();
  }

  public Optional<Colaborador> buscarPorNumeroDocumento(String documento, TipoDocumento tipoDocumento) {
    try {
      return Optional.ofNullable(
          entityManager()
              .createQuery("FROM Colaborador c WHERE c.documento = :documento AND c.tipoDoc = :tipoDocumento", Colaborador.class)
              .setParameter("documento", documento)
              .setParameter("tipoDocumento", tipoDocumento)
              .getSingleResult()
      );
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

}

