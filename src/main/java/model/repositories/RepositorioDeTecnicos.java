package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Tecnico.Tecnico;

public class RepositorioDeTecnicos implements WithSimplePersistenceUnit {

  public void guardar(Tecnico tecnico) {
    withTransaction(() -> {
      entityManager().persist(tecnico);
    });
  }

  public void modificar(Tecnico tecnico) {
    withTransaction(() -> {
      entityManager().merge(tecnico);//UPDATE
    });
  }

  public void eliminarFisico(Tecnico tecnico) {
    entityManager().remove(tecnico);//DELETE
  }

  public void eliminar(Tecnico tecnico) {
    tecnico.setActivo(false);
    entityManager().merge(tecnico);
  }

  public Optional<Tecnico> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(Tecnico.class, id));
  }

  public Tecnico buscarPorId2(Long id) {
    return entityManager().find(Tecnico.class, id);
  }

  public List<Tecnico> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Tecnico.class.getName() + " where nombre =:name")
        .setParameter("name", nombre)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Tecnico> buscarTodos() {
    return entityManager()
        .createQuery("from " + Tecnico.class.getName() + " where activo = true")
        .getResultList();
  }

}
