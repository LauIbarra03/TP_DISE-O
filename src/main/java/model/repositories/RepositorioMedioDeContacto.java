package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.MedioDeContacto.MedioDeContacto;


public class RepositorioMedioDeContacto implements WithSimplePersistenceUnit {

  public void guardar(MedioDeContacto medioDeContacto) {
    withTransaction(() -> {
      entityManager().persist(medioDeContacto);
    });
  }

  public Optional<MedioDeContacto> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(MedioDeContacto.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<MedioDeContacto> buscarTodos() {
    return entityManager()
        .createQuery("from " + MedioDeContacto.class.getName() + " where activo = true")
        .getResultList();
  }

  public void modificar(MedioDeContacto medioDeContacto) {
    withTransaction(() -> {
      entityManager().merge(medioDeContacto);
    });
  }

  public void eliminarFisico(MedioDeContacto medioDeContacto) {
    entityManager().remove(medioDeContacto);
  }

  public void eliminar(MedioDeContacto medioDeContacto) {
    medioDeContacto.setActivo(false);
    modificar(medioDeContacto);
  }
}
