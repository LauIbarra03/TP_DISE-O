package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import model.entities.Colaborador.Colaborador;
import model.entities.TipoRol.TipoRol;
import model.entities.Usuario.Usuario;

public class RepositorioUsuario implements WithSimplePersistenceUnit {

  public void guardar(Usuario usuario) {
    withTransaction(() -> {
      entityManager().persist(usuario);
    });
  }

  public Optional<Usuario> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Usuario.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Usuario> buscarTodos() {
    return entityManager()
        .createQuery("from " + Usuario.class.getName())
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Usuario> buscarPorNombre(String username) {
    return entityManager()
        .createQuery("from " + Usuario.class.getName() + " where username =: username")
        .setParameter("username", username)
        .getResultList();
  }

  public void modificar(Usuario usuario) {
    withTransaction(() -> {
      entityManager().merge(usuario);
    });
  }

  public void eliminarFisico(Usuario usuario) {
    withTransaction(() -> {
      if (entityManager().contains(usuario)) {
        entityManager().remove(usuario);
      } else {
        // Si el usuario no está gestionado, busca y elimina por ID
        Usuario managedUsuario = entityManager().find(Usuario.class, usuario.getId());
        if (managedUsuario != null) {
          entityManager().remove(managedUsuario);
        }
      }
    });
  }

  public void eliminar(Usuario usuario) {
    usuario.setActivo(false);
    modificar(usuario);
  }

  public List<Usuario> busacrPorUsuario(String username) {
    try {
      return entityManager()
          .createQuery("FROM Usuario u WHERE u.username = :user", Usuario.class)
          .setParameter("user", username)
          .getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  public Optional<TipoRol> buscarTipoRolPorColaboradorId(Long colaboradorId) {
    String query = "SELECT u.tipoRol FROM Usuario u WHERE u.colaborador.id = :colaboradorId";
    return entityManager().createQuery(query, TipoRol.class)
        .setParameter("colaboradorId", colaboradorId)
        .getResultStream()
        .findFirst();
  }

  public Optional<Usuario> findByColaborador(Colaborador colaborador) {
    try {
      return Optional.ofNullable(entityManager()
          .createQuery("SELECT u FROM Usuario u WHERE u.colaborador = :colaborador", Usuario.class)
          .setParameter("colaborador", colaborador)
          .getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }
}

