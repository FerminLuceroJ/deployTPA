package dominio.repositorios;

import dominio.entidades.heladera.incidentes.suscripciones.Suscripcion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioSuscripciones implements WithSimplePersistenceUnit {

  private static final RepositorioSuscripciones INSTANCE = new RepositorioSuscripciones();
  private RepositorioSuscripciones() {}
  public static RepositorioSuscripciones getInstance() {return INSTANCE;}

  public void persistir(Suscripcion suscripcion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(suscripcion);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<Suscripcion> buscar(){
    return entityManager().createQuery("from Suscripcion").getResultList();
  }

  public void eliminar(Suscripcion suscripcion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(suscripcion);
    tx.commit();
  }

  public Suscripcion buscarPorId(long id) {
    return entityManager().find(Suscripcion.class, id);
  }

  public List<Suscripcion> buscarPorIdColaboradorYHeladera(long idColaborador, long idHeladera) {
    return entityManager()
        .createQuery("SELECT s FROM Suscripcion s WHERE s.colaboradorHumano.id = :idColaborador AND " +
            "s.heladera.id = :idHeladera", Suscripcion.class)
        .setParameter("idColaborador", idColaborador)
        .setParameter("idHeladera", idHeladera)
        .getResultList();
  }
}
