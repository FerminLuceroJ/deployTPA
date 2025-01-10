package dominio.repositorios;

import dominio.entidades.heladera.incidentes.Incidente;
import dominio.localizacion.Localizacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityTransaction;

public class RepositorioLocalizaciones implements WithSimplePersistenceUnit {

  private static final RepositorioLocalizaciones INSTANCE = new RepositorioLocalizaciones();
  private RepositorioLocalizaciones() {}
  public static RepositorioLocalizaciones getInstance() {return INSTANCE;}

  public void persistir(Localizacion localizacion) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().persist(localizacion);
    tx.commit();
  }

  public Localizacion buscarPorId(long id) {
    return entityManager().find(Localizacion.class, id);
  }


}
