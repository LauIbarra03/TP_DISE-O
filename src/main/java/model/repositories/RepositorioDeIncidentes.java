package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import model.entities.Direccion.Localidad;
import model.entities.Incidente.Incidente;
import model.entities.Incidente.TipoIncidente;

public class RepositorioDeIncidentes implements WithSimplePersistenceUnit {

  public void guardar(Incidente incidente) {
    withTransaction(() -> {
      entityManager().persist(incidente);
    });
  }

  public void modificar(Incidente incidente) {
    withTransaction(() -> {
      entityManager().merge(incidente);//UPDATE
    });
  }

  public void eliminarFisico(Incidente incidente) {
    entityManager().remove(incidente);//DELETE
  }

  public void eliminar(Incidente incidente) {
    incidente.setActivo(false);
    entityManager().merge(incidente);
  }

  public Optional<Incidente> buscarPorId(Long id) {
    return Optional.ofNullable(entityManager().find(Incidente.class, id));
  }

  public Incidente buscarPorId2(Long id) {
    return entityManager().find(Incidente.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Incidente> buscarTodos() {
    return entityManager()
        .createQuery("from " + Incidente.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Incidente> buscarTodosLosNoResueltos() {
    return entityManager()
        .createQuery("from " + Incidente.class.getName() + " where activo = true and estaResuelto = false")
        .getResultList();
  }

  public List<Incidente> buscarPorLocalidad(Localidad localidad) {
    return entityManager()
        .createQuery("SELECT i FROM Incidente i " +
            "JOIN i.heladera h " +
            "JOIN h.puntoGeografico pg " +
            "JOIN pg.direccion d " +
            "WHERE d.localidad = :localidad " +
            "AND i.activo = true", Incidente.class)
        .setParameter("localidad", localidad)
        .getResultList();
  }

  public List<Incidente> buscarFallasDesdeFecha(LocalDate fecha) {
    return entityManager()
        .createQuery("from " + Incidente.class.getName() + " where fechaAlta >= :fecha and activo = true", Incidente.class)
        .setParameter("fecha", fecha)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Incidente> buscarAlertas(Long heladera_id) {
    return entityManager()
        .createQuery("from " + Incidente.class.getName() + " i where i.heladera.id = :heladera_id and i.tipoIncidente = Alerta")
        .setParameter("heladera_id", heladera_id)
        .getResultList();
  }

}
