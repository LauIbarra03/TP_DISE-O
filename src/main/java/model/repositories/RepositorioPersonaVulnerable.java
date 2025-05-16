package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.PersonaVulnerable.PersonaVulnerable;


public class RepositorioPersonaVulnerable implements WithSimplePersistenceUnit {

  public void guardar(PersonaVulnerable persona) {
    try {
      withTransaction(() -> {
        entityManager().persist(persona);
      });
    } catch (Exception e) {
      e.printStackTrace(); // O maneja el error de otra manera
    }
  }

  public Optional<PersonaVulnerable> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(PersonaVulnerable.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<PersonaVulnerable> buscarTodos() {
    return entityManager()
        .createQuery("from " + PersonaVulnerable.class.getName() + " where activo = true")
        .getResultList();
  }

  public void modificar(PersonaVulnerable persona) {
    withTransaction(() -> {
      entityManager().merge(persona);
    });
  }

  public void eliminarFisico(PersonaVulnerable persona) {
    entityManager().remove(persona);
  }

  public void eliminar(PersonaVulnerable persona) {
    persona.setActivo(false);
    modificar(persona);
  }

  public Long contarPersonasAsistidas() {
    return (Long) entityManager()
        .createQuery("SELECT COUNT(p) FROM PersonaVulnerable p where activo=true")
        .getSingleResult();
  }
}
