package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.entities.Direccion.Direccion;
import model.entities.Heladera.Heladera;

public class RepositorioHeladera implements WithSimplePersistenceUnit {

  public static boolean esCercana(Direccion direccion, Heladera heladera) {
    Direccion direccionHeladera = heladera.getPuntoGeografico().getDireccion();

    return direccion.getLocalidad() == direccionHeladera.getLocalidad() &&
        direccion.getCiudad() == direccionHeladera.getCiudad();
  }

  public void guardar(Heladera heladera) {
    withTransaction(() -> {
      entityManager().persist(heladera);
    });
  }

  public Optional<Heladera> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Heladera.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Heladera> buscarTodos() {
    return entityManager()
        .createQuery("from " + Heladera.class.getName() + " where activo = true")
        .getResultList();
  }

  public void withTransaction(Runnable action) {
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();
      action.run(); // Ejecuta la acción que realiza la modificación
      transaction.commit(); // Asegúrate de confirmar la transacción
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback(); // Deshacer cambios en caso de error
      }
      e.printStackTrace(); // Imprimir la excepción para obtener más detalles
      throw e; // Propagar la excepción
    }
  }

  public void modificar(Heladera heladera) {
    withTransaction(() -> {
      entityManager().merge(heladera);
    });
  }

  public void eliminarFisico(Heladera heladera) {
    entityManager().remove(heladera);
  }

  public void eliminar(Heladera heladera) {
    heladera.setActivo(false);
    modificar(heladera);
  }

  public List<Heladera> buscarCercanas(Direccion direccion) {
    return this.buscarTodos()
        .stream()
        .filter(h -> esCercana(direccion, h))
        .toList();
  }

  public List<Heladera> findAllHeladerasWithDireccion() {
    String jpql = "SELECT h FROM Heladera h JOIN FETCH h.puntoGeografico pg JOIN FETCH pg.direccion";
    TypedQuery<Heladera> query = entityManager().createQuery(jpql, Heladera.class);
    return query.getResultList();
  }

  public Long contarHeladerasActivas() {
    return (Long) entityManager()
        .createQuery("SELECT COUNT(h) FROM Heladera h WHERE h.estadoHeladera = 'ACTIVA' and activo=true")
        .getSingleResult();
  }

  @SuppressWarnings("unchecked")
  public List<Heladera> heladerasACargo(Long colaboradorId) {
    return entityManager()
        .createNativeQuery(
            "SELECT h.* FROM contribucion c " +
                "JOIN heladera h ON h.id = c.nueva_heladera_id " +
                "WHERE c.colaborador_id = :colaboradorId AND c.tipo = 'hacerseCargoHeladera'",
            Heladera.class
        )
        .setParameter("colaboradorId", colaboradorId)
        .getResultList(); // Cambiado a getResultList para devolver una lista
  }

}
