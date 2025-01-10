package dominio.repositorios;

import dominio.entidades.persona.autentificacion.Usuario;
import dominio.entidades.vianda.Vianda;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;

public class RepositorioViandas implements WithSimplePersistenceUnit {
  private static final RepositorioViandas INSTANCE = new RepositorioViandas();
  private RepositorioViandas() {}
  public static RepositorioViandas getInstance() {
    return INSTANCE;
  }

  public void persistir(Vianda vianda) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(vianda);
    tx.commit();
  }

  public Vianda buscarPorId(long id) {
    return entityManager().find(Vianda.class, id);
  }
}
