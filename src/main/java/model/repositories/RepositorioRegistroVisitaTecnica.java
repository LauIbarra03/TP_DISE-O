package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Tecnico.RegistroVisitaTecnica;


public class RepositorioRegistroVisitaTecnica implements WithSimplePersistenceUnit {


  public RepositorioRegistroVisitaTecnica() {

  }

  public void guardar(RegistroVisitaTecnica registroVisitaTecnica) {
    withTransaction(() -> {
      entityManager().persist(registroVisitaTecnica);
    });
  }

  public Optional<RegistroVisitaTecnica> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(RegistroVisitaTecnica.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<RegistroVisitaTecnica> buscarTodos() {
    return entityManager()
        .createQuery("from " + RegistroVisitaTecnica.class.getName())
        .getResultList();
  }

  public void modificar(RegistroVisitaTecnica registroVisitaTecnica) {
    withTransaction(() -> {
      entityManager().merge(registroVisitaTecnica);
    });
  }

  public void eliminarFisico(RegistroVisitaTecnica registroVisitaTecnica) {
    entityManager().remove(registroVisitaTecnica);
  }

  public void eliminar(RegistroVisitaTecnica registroVisitaTecnica) {
    registroVisitaTecnica.setActivo(false);
    modificar(registroVisitaTecnica);
  }
}
