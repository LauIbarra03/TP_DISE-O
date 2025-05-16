package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import model.entities.Direccion.Localidad;

public class RepositorioLocalidad implements WithSimplePersistenceUnit {

  public void guardar(Localidad localidad) {
    withTransaction(() -> {
      entityManager().persist(localidad);
    });
  }

  public Optional<Localidad> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Localidad.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Localidad> buscarTodos() {
    return entityManager()
        .createQuery("from " + Localidad.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Localidad> buscarPorNombre(String nombreLocalidad, String nombreProvincia) {
    return entityManager()
        .createQuery(
            "from " + Localidad.class.getName() +
                " l where l.nombre = :nombreLocalidad and l.provincia.nombre = :nombreProvincia",
            Localidad.class
        )
        .setParameter("nombreLocalidad", nombreLocalidad)
        .setParameter("nombreProvincia", nombreProvincia)
        .getResultList();
  }

  public Localidad buscarPorNombreProvinciaYLocalidad(String nombreLocalidad, String nombreProvincia) {
    try {
      return entityManager()
          .createQuery(
              "from " + Localidad.class.getName() +
                  " l where l.nombre = :nombreLocalidad and l.provincia.nombre = :nombreProvincia",
              Localidad.class
          )
          .setParameter("nombreLocalidad", nombreLocalidad)
          .setParameter("nombreProvincia", nombreProvincia)
          .getSingleResult();
    } catch (NoResultException e) {
      return null; // Retorna null si no se encuentra ninguna localidad con el nombre y provincia dados
    }
  }

  @SuppressWarnings("unchecked")
  public List<Localidad> buscarPorProvincia(Long provinciaId) {
    return entityManager()
        .createQuery("from " + Localidad.class.getName() + " WHERE activo = true AND provincia.id = :provinciaId", Localidad.class)
        .setParameter("provinciaId", provinciaId)
        .getResultList();
  }

  public List<Localidad> buscarPorProvincia(String provincia) {
    return entityManager()
        .createQuery("from " + Localidad.class.getName() + " WHERE activo = true AND provincia.nombre = :provincia", Localidad.class)
        .setParameter("provincia", provincia)
        .getResultList();
  }

  public void modificar(Localidad localidad) {
    withTransaction(() -> {
      entityManager().merge(localidad);
    });
  }

  public void eliminarFisico(Localidad localidad) {
    withTransaction(() -> {
      if (entityManager().contains(localidad)) {
        entityManager().remove(localidad);
      } else {
        // Si el localidad no está gestionado, busca y elimina por ID
        Localidad managedLocalidad = entityManager().find(Localidad.class, localidad.getId());
        if (managedLocalidad != null) {
          entityManager().remove(managedLocalidad);
        }
      }
    });
  }

  public void eliminar(Localidad localidad) {
    localidad.setActivo(false);
    modificar(localidad);
  }
}

