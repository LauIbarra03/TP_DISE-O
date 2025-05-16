package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import model.entities.Vianda.Vianda;


public class RepositorioViandas implements WithSimplePersistenceUnit {

  public void guardar(Vianda vianda) {
    try {
      withTransaction(() -> {
        entityManager().persist(vianda);
      });
    } catch (Exception e) {
      e.printStackTrace(); // O maneja el error de otra manera
    }
  }

  public void modificar(Vianda vianda) {
    withTransaction(() -> {
      entityManager().merge(vianda);//UPDATE
    });
  }

  public void eliminarFisico(Vianda vianda) {
    entityManager().remove(vianda);//DELETE
  }

  public void eliminar(Vianda vianda) {
    vianda.setActivo(false);
    modificar(vianda);
  }

  public Optional<Vianda> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(Vianda.class, id));
  }

  public Vianda buscarPorId2(Long id) {
    return entityManager().find(Vianda.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Vianda> buscarTodos() {
    return entityManager()
        .createQuery("from " + Vianda.class.getName() + " where activo = true")
        .getResultList();
  }

  public List<Vianda> buscarViandasPorHeladeraId(Long heladeraId) {
    try {
      return entityManager()
          .createQuery("FROM Vianda v WHERE v.heladera.id = :heladeraId", Vianda.class)
          .setParameter("heladeraId", heladeraId)
          .getResultList();
    } catch (NoResultException e) {
      return Collections.emptyList(); // O lanzar una excepción según prefieras
    }
  }


  public Long contarViandasEntregadas() {
    return (Long) entityManager()
        .createQuery("SELECT COUNT(v) FROM Vianda v WHERE v.estadoVianda = 'Entregada' and activo=true")
        .getSingleResult();
  }
}
