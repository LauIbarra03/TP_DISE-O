package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Tecnico.Tecnico;

public class RepositorioTecnico implements WithSimplePersistenceUnit {

  public void guardar(Tecnico tecnico) {
    withTransaction(() -> {
      entityManager().persist(tecnico);
    });
  }

  public Optional<Tecnico> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Tecnico.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Tecnico> buscarTodos() {
    return entityManager()
        .createQuery("from " + Tecnico.class.getName())
        .getResultList();
  }

  public void modificar(Tecnico tecnico) {
    withTransaction(() -> {
      entityManager().merge(tecnico);
    });
  }

  public void eliminarFisico(Tecnico tecnico) {
    entityManager().remove(tecnico);
  }

  public void eliminar(Tecnico tecnico) {
    tecnico.setActivo(false);
    modificar(tecnico);
  }
}
