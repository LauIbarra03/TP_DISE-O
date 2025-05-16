package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Suscripcion.RegistroMensaje;


public class RepositorioRegistroMensaje implements WithSimplePersistenceUnit {

  public void guardar(RegistroMensaje registroMensaje) {
    withTransaction(() -> {
      entityManager().persist(registroMensaje);
    });
  }

  public Optional<RegistroMensaje> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(RegistroMensaje.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<RegistroMensaje> buscarTodos() {
    return entityManager()
        .createQuery("from " + RegistroMensaje.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<RegistroMensaje> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + RegistroMensaje.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public void modificar(RegistroMensaje registroMensaje) {
    withTransaction(() -> {
      entityManager().merge(registroMensaje);
    });
  }

//  public void eliminarFisico(RegistroMensaje registroMensaje) {
//    withTransaction(() -> {
//      if (entityManager().contains(registroMensaje)) {
//        entityManager().remove(registroMensaje);
//      } else {
//        // Si el calle no está gestionado, busca y elimina por ID
//        RegistroMensaje managedCalle = entityManager().find(RegistroMensaje.class, RegistroMensaje.class.getId);
//        if (managedCalle != null) {
//          entityManager().remove(managedCalle);
//        }
//      }
//    });
//  }

  public void eliminar(RegistroMensaje registroMensaje) {
    registroMensaje.setActivo(false);
    modificar(registroMensaje);
  }
}