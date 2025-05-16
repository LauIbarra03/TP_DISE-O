package model.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;
import model.entities.Colaborador.Colaborador;
import model.entities.Producto.Producto;

public class RepositorioDeProductos implements WithSimplePersistenceUnit {

  public void guardar(Producto producto) {
    withTransaction(() -> {
      entityManager().persist(producto);
    });
  }

  public Optional<Producto> buscarPorID(Long id) {
    return Optional.ofNullable(entityManager().find(Producto.class, id));
  }

  @SuppressWarnings("unchecked")
  public List<Producto> buscarTodos() {
    return entityManager()
        .createQuery("from " + Producto.class.getName() + " where activo = true")
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Producto> buscarTodos(Long vendedor_id) {
    return entityManager()
        .createQuery("from " + Producto.class.getName() + " s where s.vendedor.id = :vendedor_id")
        .setParameter("vendedor_id", vendedor_id)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<Producto> buscarPorNombre(String nombre) {
    return entityManager()
        .createQuery("from " + Producto.class.getName() + " where nombre =: name")
        .setParameter("name", nombre)
        .getResultList();
  }

  public List<Producto> buscarProductosSinComprador() {
    return entityManager()
        .createQuery("from " + Producto.class.getName() + " where comprador_id is null", Producto.class)
        .getResultList();
  }

  public List<Producto> buscarProductosPorColaborador(Colaborador colaborador) {
    return entityManager()
        .createQuery("from " + Producto.class.getName() + " where comprador_id = :colaboradorId", Producto.class)
        .setParameter("colaboradorId", colaborador.getId())
        .getResultList();
  }

  public void modificar(Producto producto) {
    withTransaction(() -> {
      entityManager().merge(producto);
    });
  }

  public void eliminarFisico(Producto producto) {
    withTransaction(() -> {
      if (entityManager().contains(producto)) {
        entityManager().remove(producto);
      } else {
        Producto managedProducto = entityManager().find(Producto.class, producto.getId());
        if (managedProducto != null) {
          entityManager().remove(managedProducto);
        }
      }
    });
  }

  public void eliminar(Producto producto) {
    producto.setActivo(false);
    modificar(producto);
  }
}


