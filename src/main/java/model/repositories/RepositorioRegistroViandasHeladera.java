package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import model.entities.Heladera.RegistroViandasHeladera;

public class RepositorioRegistroViandasHeladera implements WithSimplePersistenceUnit {

  public void guardar(RegistroViandasHeladera registroViandasHeladera) {
    withTransaction(() -> {
      entityManager().persist(registroViandasHeladera);
    });
  }

  public Optional<RegistroViandasHeladera> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(RegistroViandasHeladera.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<RegistroViandasHeladera> buscarTodos() {
    return entityManager()
        .createQuery("from " + RegistroViandasHeladera.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<RegistroViandasHeladera> buscarPorFecha(LocalDate fecha) {
    return entityManager()
        .createQuery("from " + RegistroViandasHeladera.class.getName() + " where fechaAlta >= :fecha and activo = true", RegistroViandasHeladera.class)
        .setParameter("fecha", fecha)
        .getResultList();
  }

 /* @SuppressWarnings("unchecked")
  public List<Object[]> cantidadViandasPorHeladera(LocalDate fechaInicio, LocalDate fechaFin) {
    return entityManager()
        .createQuery("SELECT r.heladera.id, r.heladera.nombre, SUM(r.cantidad) " +
                "FROM " + RegistroViandasHeladera.class.getName() + " r " +
                "WHERE r.fecha BETWEEN :fechaInicio AND :fechaFin " +
                "GROUP BY r.heladera.id, r.heladera.nombre",
            Object[].class)
        .setParameter("fechaInicio", fechaInicio)
        .setParameter("fechaFin", fechaFin)
        .getResultList();
  }
*/


  @SuppressWarnings("unchecked")
  public List<RegistroViandasHeladera> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + RegistroViandasHeladera.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(RegistroViandasHeladera registroViandasHeladera) {
    withTransaction(() -> {
      entityManager().merge(registroViandasHeladera);
    });
  }

  public void eliminarFisico(RegistroViandasHeladera registroViandasHeladera) {
    withTransaction(() -> {
      if (entityManager().contains(registroViandasHeladera)) {
        entityManager().remove(registroViandasHeladera);
      } else {
        // Si el registroViandasHeladera no está gestionado, busca y elimina por ID
        RegistroViandasHeladera managedRegistroViandasHeladera = entityManager().find(RegistroViandasHeladera.class, registroViandasHeladera.getId());
        if (managedRegistroViandasHeladera != null) {
          entityManager().remove(managedRegistroViandasHeladera);
        }
      }
    });
  }

  public void eliminar(RegistroViandasHeladera registroViandasHeladera) {
    registroViandasHeladera.setActivo(false);
    modificar(registroViandasHeladera);
  }
}

