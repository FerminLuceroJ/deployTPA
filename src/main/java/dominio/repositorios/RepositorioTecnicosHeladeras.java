package dominio.repositorios;

import dominio.entidades.tecnicoHeladeras.TecnicoHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioTecnicosHeladeras implements WithSimplePersistenceUnit {

  private static final RepositorioTecnicosHeladeras INSTANCE = new RepositorioTecnicosHeladeras();
  private RepositorioTecnicosHeladeras() {}
  public static RepositorioTecnicosHeladeras getInstance() {return INSTANCE;}

  public void persistir(TecnicoHeladera tecnicoHeladera) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(tecnicoHeladera);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<TecnicoHeladera> buscar(){
    return entityManager().createQuery("from TecnicoHeladera").getResultList();
  }

  public void eliminar(TecnicoHeladera tecnicoHeladera) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(tecnicoHeladera);
    tx.commit();
  }

  public TecnicoHeladera buscarPorId(long id) {
    return entityManager().find(TecnicoHeladera.class, id);
  }
}