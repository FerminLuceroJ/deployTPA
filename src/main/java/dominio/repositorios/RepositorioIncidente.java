package dominio.repositorios;

import dominio.entidades.heladera.Heladera;
import dominio.entidades.heladera.incidentes.Incidente;
import java.util.List;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;

public class RepositorioIncidente implements WithSimplePersistenceUnit {

  private static final RepositorioIncidente INSTANCE = new RepositorioIncidente();
  private RepositorioIncidente() {}
  public static RepositorioIncidente getInstance() {return INSTANCE;}

  public void persistir(Incidente incidente) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(incidente);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<Incidente> buscar(){
    return entityManager().createQuery("from Incidente").getResultList();
  }

  public void eliminar(Incidente incidente) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(incidente);
    tx.commit();
  }

  public Incidente buscarPorId(long id) {
    return entityManager().find(Incidente.class, id);
  }

  public List<Incidente> buscarPorHeladera(long id) {
    String jpql = "SELECT i FROM Incidente i WHERE i.heladera.id = :heladeraId";
    return entityManager().createQuery(jpql, Incidente.class)
        .setParameter("heladeraId", id)
        .getResultList();
  }

  public List<Heladera> buscarIncidentsePorIdHeladera(long id) {
    return entityManager().createQuery("from Heladera h where h.id = :id", Heladera.class)
        .setParameter("id", id)
        .getResultList();
  }

}
