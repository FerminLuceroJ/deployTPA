package dominio.repositorios;

import dominio.entidades.formulario.Formulario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;

public class RepositorioFormularios implements WithSimplePersistenceUnit {

  private static final RepositorioFormularios INSTANCE = new RepositorioFormularios();
  private RepositorioFormularios() {}
  public static RepositorioFormularios getInstance() {
    return INSTANCE;
  }

  public void persistir(Formulario formulario) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(formulario);
    tx.commit();
  }

  public Formulario buscar(long id) {
    return entityManager().find(Formulario.class, id);
  }
}
