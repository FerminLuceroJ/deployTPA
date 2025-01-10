package dominio.repositorios;

import java.util.List;
import dominio.entidades.persona.autentificacion.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

public class RepositorioUsuarios implements WithSimplePersistenceUnit {

  private static final RepositorioUsuarios INSTANCE = new RepositorioUsuarios();
  private RepositorioUsuarios() {}
  public static RepositorioUsuarios getInstance() {
    return INSTANCE;
  }

  public void persistir(Usuario usuario) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(usuario);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<Usuario> buscar() {
    return entityManager().createQuery("from Usuario").getResultList();
  }

  public void modificar(Usuario usuario) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().merge(usuario);
    tx.commit();
  }

  public void eliminar(Usuario usuario) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(usuario);
    tx.commit();
  }

  public Usuario buscarPorId(long id) {
    return entityManager().find(Usuario.class, id);
  }


  // EL PROBLEMA ES QUE DEVUELVE VARIOS - ARREGLAR
  public Usuario buscarPorEmail(String email) {
    try {
      return entityManager().createQuery("from Usuario where email = :email", Usuario.class)
          .setParameter("email", email)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
