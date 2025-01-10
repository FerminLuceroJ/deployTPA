package dominio.repositorios;

import dominio.notificador.Notificacion;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioNotificaciones implements WithSimplePersistenceUnit {

  private static final RepositorioNotificaciones INSTANCE = new RepositorioNotificaciones();
  private RepositorioNotificaciones() {}
  public static RepositorioNotificaciones getInstance() {return INSTANCE;}

  public void persistir(Notificacion notificacion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(notificacion);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<Notificacion> buscar(){
    return entityManager().createQuery("from Notificacion").getResultList();
  }

  public void eliminar(Notificacion notificacion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(notificacion);
    tx.commit();
  }

  public Notificacion buscarPorId(long id) {
    return entityManager().find(Notificacion.class, id);
  }

  @SuppressWarnings("unchecked")
  public List<Notificacion> buscarPorIdColaborador(long idColaborador) {
    List<Notificacion> notificaciones =  entityManager().createQuery("FROM Notificacion n WHERE n.colaborador.id = :idColaborador", Notificacion.class).
        setParameter("idColaborador", idColaborador)
        .getResultList();

    notificaciones.forEach(notificacion -> entityManager().refresh(notificacion));

    return notificaciones;
  }

  public List<Notificacion> buscarNotifacacionesPorIdSugerencia(long id) {
    return entityManager().createQuery("FROM Notificacion n WHERE n.sugerencia.id = :id", Notificacion.class).
        setParameter("id", id)
        .getResultList();
  }

  public void actualizar(Notificacion notificacion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().merge(notificacion);
    entityManager().flush();
    tx.commit();

  }

}
