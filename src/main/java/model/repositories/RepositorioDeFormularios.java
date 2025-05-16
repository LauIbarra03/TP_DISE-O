package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Formulario.Formulario;

public class RepositorioDeFormularios implements WithSimplePersistenceUnit {

  public void guardar(Formulario formulario) {
    withTransaction(() -> {
      entityManager().persist(formulario);
    });
  }

  public void modificar(Formulario formulario) {
    withTransaction(() -> {
      entityManager().merge(formulario);//UPDATE
    });
  }

  public void eliminarFisico(Formulario formulario) {
    entityManager().remove(formulario);//DELETE
  }

  public void eliminar(Formulario formulario) {
    formulario.setActivo(false);
    entityManager().merge(formulario);
  }

  public Optional<Formulario> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(Formulario.class, id));
  }

  public Formulario buscarPorId2(Long id) {
    return entityManager().find(Formulario.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Formulario> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Formulario.class.getName() + " where nombre =:name")
        .setParameter("name", nombre)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Formulario> buscarTodos() {
    return entityManager()
        .createQuery("from " + Formulario.class.getName() + " where activo = true")
        .getResultList();
  }

}
