package dominio.repositorios;

import dominio.entidades.tarjeta.solicitudes.SolicitudTarjeta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioSolicitudesTarjeta implements WithSimplePersistenceUnit {

  private static final RepositorioSolicitudesTarjeta INSTANCE = new RepositorioSolicitudesTarjeta();
  private RepositorioSolicitudesTarjeta() {}
  public static RepositorioSolicitudesTarjeta getInstance() {return INSTANCE;}

  public void persistir(SolicitudTarjeta solicitudTarjeta) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(solicitudTarjeta);
    tx.commit();
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudTarjeta> buscar(){
    return entityManager().createQuery("from SolicitudTarjeta").getResultList();
  }

  public void eliminar(SolicitudTarjeta solicitudTarjeta) {
    entityManager().remove(solicitudTarjeta);
  }

  public SolicitudTarjeta buscarPorId(long id) {
    return entityManager().find(SolicitudTarjeta.class, id);
  }

}
