package dominio.repositorios;

import dominio.entidades.tecnicoHeladeras.RegistroVisita;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioRegistroVisita implements WithSimplePersistenceUnit {

  private static final RepositorioRegistroVisita INSTANCE = new RepositorioRegistroVisita();
  private RepositorioRegistroVisita() {}
  public static RepositorioRegistroVisita getInstance() {return INSTANCE;}

  public void persistir(RegistroVisita registroVisita) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(registroVisita);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<RegistroVisita> buscar(){
    return entityManager().createQuery("from RegistroVisita").getResultList();
  }

  public void eliminar(RegistroVisita visita) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(visita);
    tx.commit();
  }

  public RegistroVisita buscarPorId(long id) {
    return entityManager().find(RegistroVisita.class, id);
  }

}