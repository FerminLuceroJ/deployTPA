package dominio.repositorios;

import java.util.List;

import dominio.entidades.heladera.aperturas.SolicitudApertura;
import dominio.entidades.heladera.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;

public class RepositoriosSolicitudAperturas implements WithSimplePersistenceUnit {

  private static final RepositoriosSolicitudAperturas INSTANCE = new RepositoriosSolicitudAperturas();
  private RepositoriosSolicitudAperturas() {}
  public static RepositoriosSolicitudAperturas getInstance() {return INSTANCE;}

  public void persistir(SolicitudApertura solicitudApertura) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(solicitudApertura);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudApertura> buscar(){
    return entityManager().createQuery("from SolicitudApertura ").getResultList();
  }

  public void eliminar(SolicitudApertura solicitudApertura) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(solicitudApertura);
    tx.commit();
  }

  public SolicitudApertura buscarPorId(long id) {
    return entityManager().find(SolicitudApertura.class, id);
  }
}
