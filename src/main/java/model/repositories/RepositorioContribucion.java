package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import model.entities.Contribucion.Contribucion;
import model.entities.Contribucion.DonacionVianda.DonacionVianda;

public class RepositorioContribucion implements WithSimplePersistenceUnit {

  public void guardar(Contribucion contribucion) {
    withTransaction(() -> {
      entityManager().persist(contribucion);
    });
  }

  public Optional<Contribucion> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Contribucion.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Contribucion> buscarTodos() {
    return entityManager()
        .createQuery("from " + Contribucion.class.getName() + " where activo = true")
        .getResultList();
  }


  @SuppressWarnings("unchecked")
  public List<Contribucion> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Contribucion.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public List<Contribucion> buscarPorColaboradorId(Long colaboradorId) {
    return entityManager()
        .createQuery("FROM Contribucion WHERE colaborador.id = :colaboradorId", Contribucion.class)
        .setParameter("colaboradorId", colaboradorId)
        .getResultList();
  }

  public void modificar(Contribucion contribucion) {
    withTransaction(() -> {
      entityManager().merge(contribucion);
    });
  }

  public void eliminarFisico(Contribucion contribucion) {
    entityManager().remove(contribucion);
  }

  public void eliminar(Contribucion contribucion) {
    contribucion.setActivo(false);
    modificar(contribucion);
  }

  public List<Contribucion> buscarContribucionesPorColaborador(Long colaboradorId) {
    return entityManager()
        .createQuery("FROM Contribucion WHERE colaborador.id = :colaborador_id", Contribucion.class)
        .setParameter("colaborador_id", colaboradorId)
        .getResultList();
  }

  public Long contarDistribucionViandasPorColaboradorId(Long colaboradorId) {
    return entityManager()
        .createQuery("SELECT COUNT(c) FROM " + Contribucion.class.getName() + " c WHERE c.colaborador.id = :colaboradorId AND TYPE(c) = DistribucionViandas", Long.class)
        .setParameter("colaboradorId", colaboradorId)
        .getSingleResult();
  }

  public Long sumarDonacionDineroPorColaboradorId(Long colaboradorId) {
    return (Long) entityManager()
        .createQuery("SELECT SUM(c.cantidadDinero) FROM " + Contribucion.class.getName() + "  c WHERE c.colaborador.id = :colaboradorId AND TYPE(c) = DonacionDinero ")
        .setParameter("colaboradorId", colaboradorId)
        .getSingleResult();
  }

  public Long contarPersonasVulnerablesRegistradasPorColaboradorId(Long colaboradorId) {
    return entityManager()
        .createQuery("SELECT COUNT(c) FROM " + Contribucion.class.getName() + " c WHERE c.colaborador.id = :colaboradorId AND TYPE(c) = RegistroPersonaVulnerable", Long.class)
        .setParameter("colaboradorId", colaboradorId)
        .getSingleResult();
  }

  public Long contarRealizarOfertasPorColaboradorId(Long colaboradorId) {
    return entityManager()
        .createQuery("SELECT COUNT(c) FROM " + Contribucion.class.getName() + " c WHERE c.colaborador.id = :colaboradorId AND TYPE(c) = RealizarOfertas", Long.class)
        .setParameter("colaboradorId", colaboradorId)
        .getSingleResult();
  }

  public Long contarHacerseCargoHeladeraPorColaboradorId(Long colaboradorId) {
    return entityManager()
        .createQuery("SELECT COUNT(c) FROM " + Contribucion.class.getName() + " c WHERE c.colaborador.id = :colaboradorId AND TYPE(c) = HacerseCargoHeladera", Long.class)
        .setParameter("colaboradorId", colaboradorId)
        .getSingleResult();
  }

  public Long contarDonacionViandaPorColaboradorId(Long colaboradorId) {
    return entityManager()
        .createQuery("SELECT COUNT(c) FROM " + Contribucion.class.getName() + " c WHERE c.colaborador.id = :colaboradorId AND TYPE(c) = DonacionVianda", Long.class)
        .setParameter("colaboradorId", colaboradorId)
        .getSingleResult();
  }


  @SuppressWarnings("unchecked")
  public List<DonacionVianda> buscarDonacionPorFecha(LocalDate fecha) {
    return entityManager()
        .createNativeQuery(
            "SELECT * FROM contribucion " +
                "WHERE tipo = 'donacionVianda' " +
                "AND fechaAlta >= :fecha " +
                "AND activo = true",
            DonacionVianda.class
        )
        .setParameter("fecha", fecha)
        .getResultList();
  }


}
