package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Colaborador.TipoOrganizacion;


public class RepositorioTipoOrganizacion implements WithSimplePersistenceUnit {

  public void guardar(TipoOrganizacion tipoOrganizacion) {
    withTransaction(() -> {
      entityManager().persist(tipoOrganizacion);
    });
  }

  public Optional<TipoOrganizacion> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(TipoOrganizacion.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<TipoOrganizacion> buscarTodos() {
    return entityManager()
        .createQuery("from " + TipoOrganizacion.class.getName())
        .getResultList();
  }

  public void modificar(TipoOrganizacion tipoOrganizacion) {
    withTransaction(() -> {
      entityManager().merge(tipoOrganizacion);
    });
  }

  public void eliminarFisico(TipoOrganizacion tipoOrganizacion) {
    entityManager().remove(tipoOrganizacion);
  }

}
