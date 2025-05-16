package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import model.entities.Colaborador.TipoPersona;


public class RepositorioTipoPersona implements WithSimplePersistenceUnit {


  public RepositorioTipoPersona() {

  }

  public void guardar(TipoPersona tipoPersona) {
    withTransaction(() -> {
      entityManager().persist(tipoPersona);
    });
  }

  public Optional<TipoPersona> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(TipoPersona.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<TipoPersona> buscarTodos() {
    return entityManager()
        .createQuery("from " + TipoPersona.class.getName())
        .getResultList();
  }

  public void modificar(TipoPersona tipoPersona) {
    withTransaction(() -> {
      entityManager().merge(tipoPersona);
    });
  }

  public void eliminarFisico(TipoPersona tipoPersona) {
    entityManager().remove(tipoPersona);
  }

  public void eliminar(TipoPersona tipoPersona) {
    tipoPersona.setActivo(false);
    modificar(tipoPersona);
  }

  public Optional<TipoPersona> buscarPorNombre(String nombre) {
    try {
      return Optional.ofNullable(entityManager().createQuery("SELECT t FROM TipoPersona t WHERE t.nombre = :nombre", TipoPersona.class)
          .setParameter("nombre", nombre)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  public TipoPersona obtenerTipoPersona(String tipo) {
    if (tipo.equals("Humana")) {
      return this.buscarPorNombre("Humana").orElse(null);
    } else if (tipo.equals("Jurídica")) {
      return this.buscarPorNombre("Jurídica").orElse(null);
    }
    return null; // O lanzar excepción si el tipo no es válido
  }

}
